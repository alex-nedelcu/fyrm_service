package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.FindInitiatorStatusCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.FindInitiatorStatusUseCase;
import com.fyrm.fyrm_service.application.port.out.FindRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.FindRentMateProposalPort;
import com.fyrm.fyrm_service.domain.InitiatorCurrentState;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.RentMateProposal;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

import static com.fyrm.fyrm_service.domain.InitiatorStatus.CAN_CREATE;
import static com.fyrm.fyrm_service.domain.InitiatorStatus.CAN_FINALISE;
import static com.fyrm.fyrm_service.domain.InitiatorStatus.MUST_WAIT;
import static java.time.Duration.between;

@UseCase
@RequiredArgsConstructor
public class FindInitiatorStatusService implements FindInitiatorStatusUseCase {

  private static final long RENT_CONNECTION_MINUTES_LIMIT = 30L;
  private final FindRentConnectionPort findRentConnectionPort;
  private final FindRentMateProposalPort findRentMateProposalPort;

  @Override
  public InitiatorCurrentState findStatus(FindInitiatorStatusCommand findInitiatorStatusCommand) {
    Long userId = findInitiatorStatusCommand.getUserId();

    if (!findRentConnectionPort.hasAnyActive(userId)) {
      return InitiatorCurrentState.with(CAN_CREATE);
    }

    RentConnection latestActive = findRentConnectionPort
        .findLatestActiveByUserId(userId)
        .orElseThrow(() -> new ResourceNotFoundException("Latest active rent connection of user with id " + userId + " not found"));
    RentMateProposal proposal = findRentMateProposalPort
        .findByRentConnectionId(latestActive.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Proposal associated to rent connection with id " + latestActive.getId() + " not found"));

    if (canFinalise(latestActive)) {
      return InitiatorCurrentState.with(CAN_FINALISE, latestActive, proposal);
    }

    long minutesToWait = RENT_CONNECTION_MINUTES_LIMIT - getMinutesPassedSince(latestActive);
    return InitiatorCurrentState.with(MUST_WAIT, minutesToWait, latestActive, proposal);
  }

  private boolean canFinalise(RentConnection latestActive) {
    long minutesPassed = getMinutesPassedSince(latestActive);
    return minutesPassed >= RENT_CONNECTION_MINUTES_LIMIT;
  }

  private long getMinutesPassedSince(RentConnection rentConnection) {
    return between(rentConnection.getCreatedAt(), ZonedDateTime.now()).toMinutes();
  }
}
