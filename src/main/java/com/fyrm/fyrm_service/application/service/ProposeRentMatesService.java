package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ProposeRentMatesCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ProposeRentMatesUseCase;
import com.fyrm.fyrm_service.application.port.out.FindSearchProfilePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentMateProposalPort;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.RentConnectionStatus;
import com.fyrm.fyrm_service.domain.RentMateProposal;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ProposeRentMatesService implements ProposeRentMatesUseCase {

  private final FindUserPort findUserPort;
  private final FindSearchProfilePort findSearchProfilePort;
  private final PersistRentConnectionPort persistRentConnectionPort;
  private final PersistRentMateProposalPort persistRentMateProposalPort;

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
        .build();
  }

  private RentMateProposal generateRentMateProposal(ProposeRentMatesCommand proposeRentMatesCommand, Long rentConnectionId) {
    Long initiatorId = proposeRentMatesCommand.getInitiatorId();
    User initiator = findUserPort.findById(initiatorId).orElseThrow(() -> new ResourceNotFoundException("Initiator not found"));

    int proposalMaximumSize = proposeRentMatesCommand.getProposalMaximumSize();
    List<SearchProfile> initiatorUsedSearchProfiles = findSearchProfilePort.findAllByIds(proposeRentMatesCommand.getSearchProfileIds());
    List<User> allUsersExceptInitiator = findUserPort.findAll().stream().filter(user -> user.isNot(initiator)).toList();

    // Algorithm start (mock)
    List<User> proposed = new ArrayList<>();
    proposed.addAll(allUsersExceptInitiator.subList(0, proposalMaximumSize));
    // Algorithm end (mock)

    RentMateProposal proposal = RentMateProposal.builder()
        .rentConnectionId(rentConnectionId)
        .proposedRentMates(proposed)
        .build();

    return proposal;
  }
}
