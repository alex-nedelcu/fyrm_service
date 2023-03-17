package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ProposeRentMatesCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ProposeRentMatesUseCase;
import com.fyrm.fyrm_service.application.port.out.FindRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.FindRentMateProposalPort;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ProposeRentMatesService implements ProposeRentMatesUseCase {

  private static final int AVOID_FAILED_PROPOSALS_NEWER_THAN_DAYS = 7;
  private static final Double MINIMUM_VALUE = -Double.MAX_VALUE;
  private final FindUserPort findUserPort;
  private final FindRentConnectionPort findRentConnectionPort;
  private final FindRentMateProposalPort findRentMateProposalPort;
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
    int proposalMaximumSize = proposeRentMatesCommand.getProposalMaximumSize();
    var initiatorUsedSearchProfiles = findSearchProfilePort.findAllByIds(proposeRentMatesCommand.getSearchProfileIds());
    var candidates = findUserPort.findSearchingUsersExcept(initiatorId);

    var recentFailedRentConnectionIds = findRentConnectionPort.findFailedByUserIdNewerThanDays(initiatorId, AVOID_FAILED_PROPOSALS_NEWER_THAN_DAYS);
    var blackListedCandidateIds = findRentMateProposalPort.findProposedUserIdsForRentConnections(recentFailedRentConnectionIds);
    var proposedRentMates = findMatchingRentMates(initiatorUsedSearchProfiles, candidates, blackListedCandidateIds, proposalMaximumSize, rentConnectionId);

    return RentMateProposal.builder()
        .rentConnectionId(rentConnectionId)
        .proposedRentMates(proposedRentMates)
        .build();
  }

  private List<ProposedRentMate> findMatchingRentMates(
      List<SearchProfile> initiatorSearchProfiles,
      List<User> candidates,
      List<Long> blackListedIds,
      int maximumSize,
      Long rentConnectionId
  ) {
    var candidateIdToHighestScore = new HashMap<Long, Double>();

    var candidatesSearchProfiles = findSearchProfilePort.findAllByUsers(candidates);
    for (var ofInitiator : initiatorSearchProfiles) {
      for (var ofCandidate : candidatesSearchProfiles) {

        double score = matchMakingService.computeMatchingScore(ofInitiator, ofCandidate);
        Long candidateId = ofCandidate.getUser().getId();

        var previousScore = candidateIdToHighestScore.getOrDefault(candidateId, MINIMUM_VALUE);
        candidateIdToHighestScore.put(candidateId, Math.max(score, previousScore));
      }
    }

    var whiteListedCandidateIdToHighestScore = new HashMap<>(candidateIdToHighestScore);
    blackListedIds.forEach(whiteListedCandidateIdToHighestScore::remove);

    var pollutedProposedRentMates = toProposedRentMatesOrderedByBestScores(candidateIdToHighestScore, rentConnectionId);
    var whiteListedProposedRentMates = toProposedRentMatesOrderedByBestScores(whiteListedCandidateIdToHighestScore, rentConnectionId);

    if (!whiteListedProposedRentMates.isEmpty()) {
      return whiteListedProposedRentMates.subList(0, Math.min(maximumSize, whiteListedProposedRentMates.size()));
    }

    return pollutedProposedRentMates.subList(0, Math.min(maximumSize, pollutedProposedRentMates.size()));
  }

  private List<ProposedRentMate> toProposedRentMatesOrderedByBestScores(Map<Long, Double> userIdsToScores, Long rentConnectionId) {
    return userIdsToScores
        .entrySet()
        .stream()
        .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
        .map(Entry::getKey)
        .map(userId -> findUserPort.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Candidate not found!")))
        .map(user -> ProposedRentMate.builder()
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
