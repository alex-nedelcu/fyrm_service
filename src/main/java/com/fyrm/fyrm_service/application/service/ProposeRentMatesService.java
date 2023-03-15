package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ProposeRentMatesCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ProposeRentMatesUseCase;
import com.fyrm.fyrm_service.application.port.out.FindSearchProfilePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentMateProposalPort;
import com.fyrm.fyrm_service.application.service.match_making.MatchMakingService;
import com.fyrm.fyrm_service.domain.ProposedRentMate;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.RentConnectionStatus;
import com.fyrm.fyrm_service.domain.RentMateProposal;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ProposeRentMatesService implements ProposeRentMatesUseCase {

  private final FindUserPort findUserPort;
  private final FindSearchProfilePort findSearchProfilePort;
  private final PersistRentConnectionPort persistRentConnectionPort;
  private final PersistRentMateProposalPort persistRentMateProposalPort;
  private final MatchMakingService matchMakingService;

  @Override
  @Transactional
  public RentMateProposal propose(ProposeRentMatesCommand proposeRentMatesCommand) {
    RentConnection rentConnection = convertToRentConnectionWithStatus(proposeRentMatesCommand, RentConnectionStatus.ACTIVE);
    var rentConnectionId = persistRentConnectionPort.persistAndFlush(rentConnection);

    RentMateProposal proposal = generateRentMateProposal(proposeRentMatesCommand, rentConnectionId);
    var proposalId = persistRentMateProposalPort.persist(proposal);
    proposal.setId(proposalId);

    return proposal;
  }

  private RentConnection convertToRentConnectionWithStatus(ProposeRentMatesCommand proposeRentMatesCommand, RentConnectionStatus status) {
    return RentConnection.builder()
        .initiatorId(proposeRentMatesCommand.getInitiatorId())
        .proposalMaximumSize(proposeRentMatesCommand.getProposalMaximumSize())
        .status(status)
        .usedSearchProfiles(findSearchProfilePort.findAllByIds(proposeRentMatesCommand.getSearchProfileIds()))
        .createdAt(ZonedDateTime.now())
        .build();
  }

  private RentMateProposal generateRentMateProposal(ProposeRentMatesCommand proposeRentMatesCommand, Long rentConnectionId) {
    Long initiatorId = proposeRentMatesCommand.getInitiatorId();
    User initiator = findUserPort.findById(initiatorId).orElseThrow(() -> new ResourceNotFoundException("Initiator not found"));

    List<SearchProfile> initiatorUsedSearchProfiles = findSearchProfilePort.findAllByIds(proposeRentMatesCommand.getSearchProfileIds());
    List<User> potentialRentMates = findUserPort.findAll().stream().filter(user -> user.isNot(initiator) && user.getIsSearching()).toList();
    int proposalMaximumSize = Math.min(proposeRentMatesCommand.getProposalMaximumSize(), potentialRentMates.size());

    var proposed = findMatchingRentMates(initiatorUsedSearchProfiles, potentialRentMates, proposalMaximumSize, rentConnectionId);

    RentMateProposal proposal = RentMateProposal.builder()
        .rentConnectionId(rentConnectionId)
        .proposedRentMates(proposed)
        .build();

    return proposal;
  }

  private List<ProposedRentMate> findMatchingRentMates(
      List<SearchProfile> initiatorSearchProfiles,
      List<User> candidates,
      int maximumSize,
      Long rentConnectionId) {
    var candidateIdToHighestScore = new HashMap<Long, Double>();

    for (var initiatorSearchProfile : initiatorSearchProfiles) {
      for (var candidate : candidates) {
        List<SearchProfile> candidateSearchProfiles = findSearchProfilePort.findAllByUserId(candidate.getId());
        for (var candidateSearchProfile : candidateSearchProfiles) {
          double score = matchMakingService.computeMatchingScore(initiatorSearchProfile, candidateSearchProfile);
          System.out.println("Score between search profiles " + initiatorSearchProfile.getId() + " and " + candidateSearchProfile.getId() + " was " + score);

          if (score > candidateIdToHighestScore.getOrDefault(candidate.getId(), Double.MIN_VALUE)) {
            candidateIdToHighestScore.put(candidate.getId(), score);
          }
        }
      }
    }

    return chooseBestMatches(candidateIdToHighestScore, maximumSize, rentConnectionId);
  }

  private List<ProposedRentMate> chooseBestMatches(Map<Long, Double> userIdsToScores, int maximumSize, Long rentConnectionId) {
    return userIdsToScores
        .entrySet()
        .stream()
        .sorted(Entry.comparingByValue())
        .limit(maximumSize)
        .map(Entry::getKey)
        .map(userId -> findUserPort.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Candidate not found!")))
        .map(user ->
            ProposedRentMate.builder()
                .rentConnectionId(rentConnectionId)
                .userId(user.getId())
                .email(user.getEmail())
                .description(user.getDescription())
                .username(user.getUsername())
                .build()
        )
        .toList();
  }
}
