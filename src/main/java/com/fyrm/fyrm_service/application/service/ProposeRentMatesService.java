package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ProposeRentMatesCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ProposeRentMatesUseCase;
import com.fyrm.fyrm_service.application.port.out.FindRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.FindRentMateProposalPort;
import com.fyrm.fyrm_service.application.port.out.FindSearchProfilePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistNotificationPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentMateProposalPort;
import com.fyrm.fyrm_service.application.port.out.SendNotificationPort;
import com.fyrm.fyrm_service.application.service.match_making.MatchMakingService;
import com.fyrm.fyrm_service.domain.Notification;
import com.fyrm.fyrm_service.domain.ProposedRentMate;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.RentConnectionStatus;
import com.fyrm.fyrm_service.domain.RentMateProposal;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ProposeRentMatesService implements ProposeRentMatesUseCase {

  private static final int AVOID_RENT_MATES_PROPOSED_DURING_LAST_DAYS = 1;
  private static final Long BLACKLIST_SCORE_THRESHOLD = -1000L;
  private static final String PROPOSED_RENT_MATE_NOTIFICATION_PREVIEW = "Congratulations, you have been chosen as a potential rent mate! Click here to chat with the initiator";
  private static final Double MINIMUM_VALUE = -Double.MAX_VALUE;
  private final FindUserPort findUserPort;
  private final FindRentConnectionPort findRentConnectionPort;
  private final FindRentMateProposalPort findRentMateProposalPort;
  private final FindSearchProfilePort findSearchProfilePort;
  private final PersistRentConnectionPort persistRentConnectionPort;
  private final PersistRentMateProposalPort persistRentMateProposalPort;
  private final SendNotificationPort sendNotificationPort;
  private final PersistNotificationPort persistNotificationPort;
  private final MatchMakingService matchMakingService;

  @Override
  @Transactional
  public RentMateProposal propose(ProposeRentMatesCommand proposeRentMatesCommand) {
    RentConnection rentConnection = convertToRentConnectionWithStatus(proposeRentMatesCommand, RentConnectionStatus.ACTIVE);
    var rentConnectionId = persistRentConnectionPort.persistAndFlush(rentConnection);

    RentMateProposal proposal = generateRentMateProposal(proposeRentMatesCommand, rentConnectionId);
    var proposalId = persistRentMateProposalPort.persist(proposal);
    proposal.setId(proposalId);
    notifyProposedRentMates(proposeRentMatesCommand.getInitiatorId(), proposal.getProposedRentMates());

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

    var recentRentConnectionIds = findRentConnectionPort.findAllByUserIdNewerThanDays(initiatorId, AVOID_RENT_MATES_PROPOSED_DURING_LAST_DAYS);
    var blackListedCandidateIds = findRentMateProposalPort.findProposedUserIdsForRentConnections(recentRentConnectionIds);
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

    var whiteListedCandidateIdToHighestScore = new HashMap<Long, Double>();
    var blackListedCandidateIdToHighestScore = new HashMap<Long, Double>();

    // Split all candidates into:
    // black listed -> those who were recently suggested or who have a terrible score
    // white listed -> the others
    candidateIdToHighestScore.forEach((candidateId, score) -> {
      if (score < BLACKLIST_SCORE_THRESHOLD || blackListedIds.contains(candidateId)) {
        blackListedCandidateIdToHighestScore.put(candidateId, score);
      } else {
        whiteListedCandidateIdToHighestScore.put(candidateId, score);
      }
    });

    var blackListedProposedRentMates = toProposedRentMatesOrderedByBestScores(blackListedCandidateIdToHighestScore, rentConnectionId);
    var whiteListedProposedRentMates = toProposedRentMatesOrderedByBestScores(whiteListedCandidateIdToHighestScore, rentConnectionId);

    if (!whiteListedProposedRentMates.isEmpty()) {

      // There are more white listed candidates than the selection size => there are some extra candidates
      if (whiteListedProposedRentMates.size() > maximumSize) {
        var bestPartition = whiteListedProposedRentMates.subList(0, maximumSize);
        var extraPartition = whiteListedProposedRentMates.subList(maximumSize, whiteListedProposedRentMates.size());

        // Replace last candidate from the best partition with one random candidate from extra partition
        Collections.shuffle(extraPartition);
        var selected = bestPartition.subList(0, bestPartition.size() - 1);
        selected.add(extraPartition.get(0));

        return selected;
      }

      // The number of white listed candidates is less or equal than the selection size => return all of them
      return whiteListedProposedRentMates.subList(0, whiteListedProposedRentMates.size());
    }

    // From the black listed rent mates, suggest completely random
    Collections.shuffle(blackListedProposedRentMates);
    return blackListedProposedRentMates.subList(0, Math.min(maximumSize, blackListedProposedRentMates.size()));
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
        .collect(Collectors.toList());
  }

  private void notifyProposedRentMates(Long initiatorId, List<ProposedRentMate> proposedRentMates) {
    User initiator = findUserPort.findById(initiatorId).orElseThrow(() -> new ResourceNotFoundException("Notifications initiator not found!"));

    proposedRentMates.forEach(rentMate -> {
      var notification = Notification.builder()
          .preview(PROPOSED_RENT_MATE_NOTIFICATION_PREVIEW)
          .fromId(initiator.getId())
          .fromUsername(initiator.getUsername())
          .toId(rentMate.getUserId())
          .toUsername(rentMate.getUsername())
          .sentAt(ZonedDateTime.now())
          .isRead(false)
          .build();

      var savedNotification = persistNotificationPort.persist(notification);
      sendNotificationPort.send(savedNotification);
    });
  }
}
