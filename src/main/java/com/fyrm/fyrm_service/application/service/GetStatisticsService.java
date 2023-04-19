package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.GetStatisticsByUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.GetStatisticsUseCase;
import com.fyrm.fyrm_service.application.port.out.FindMessagePort;
import com.fyrm.fyrm_service.application.port.out.FindRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.FindRentMateProposalPort;
import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.domain.ProposedRentMate;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.RentMateProposal;
import com.fyrm.fyrm_service.domain.Statistics;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class GetStatisticsService implements GetStatisticsUseCase {

  private final FindMessagePort findMessagePort;
  private final FindRentConnectionPort findRentConnectionPort;
  private final FindRentMateProposalPort findRentMateProposalPort;

  @Override
  public Statistics getByUser(GetStatisticsByUserCommand getStatisticsByUserCommand) {
    Long userId = getStatisticsByUserCommand.getUserId();
    var rentConnectionsByUser = findRentConnectionPort.findAllInitiatedBy(userId);
    var proposedIn = findRentMateProposalPort.findByProposedRentMatesContain(userId);
    var chats = findMessagePort.findAllSentOrReceivedBy(userId);

    return Statistics.builder()
        .chattedWithUsers(extractCorrespondentsUniqueIds(userId, chats))
        .suggestedToUsers(extractSuggestedToUsers(proposedIn))
        .suggestedUsers(extractSuggestedUsers(rentConnectionsByUser))
        .build();
  }

  private List<Long> extractCorrespondentsUniqueIds(Long currentUserId, List<ChatMessage> chats) {
    var from = chats.stream().map(ChatMessage::getFromId).collect(Collectors.toSet());
    var to = chats.stream().map(ChatMessage::getToId).collect(Collectors.toSet());

    var allExceptCurrentUser = new HashSet<Long>();
    allExceptCurrentUser.addAll(from);
    allExceptCurrentUser.addAll(to);
    allExceptCurrentUser.remove(currentUserId);

    return allExceptCurrentUser.stream().toList();
  }

  private List<Long> extractSuggestedToUsers(List<RentMateProposal> proposals) {
    return proposals.stream()
        .map(proposal -> findRentConnectionPort.findById(proposal.getRentConnectionId()).orElseThrow(ResourceNotFoundException::new))
        .map(RentConnection::getInitiatorId)
        .collect(Collectors.toSet())
        .stream()
        .toList();
  }

  private List<Long> extractSuggestedUsers(List<RentConnection> rentConnections) {
    return rentConnections.stream()
        .map(RentConnection::getId)
        .map(rentConnectionId -> findRentMateProposalPort.findByRentConnectionId(rentConnectionId).orElseThrow(ResourceNotFoundException::new))
        .map(RentMateProposal::getProposedRentMates)
        .flatMap(List::stream)
        .map(ProposedRentMate::getUserId)
        .collect(Collectors.toSet())
        .stream()
        .toList();
  }
}
