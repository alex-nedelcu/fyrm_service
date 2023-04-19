package com.fyrm.fyrm_service.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InitiatorCurrentState {

  InitiatorStatus status;
  long minutesToWait;
  RentConnection rentConnection;
  RentMateProposal rentMateProposal;

  public static InitiatorCurrentState with(InitiatorStatus status) {
    return builder().status(status).build();
  }

  public static InitiatorCurrentState with(InitiatorStatus status, long minutesToWait, RentConnection rentConnection, RentMateProposal rentMateProposal) {
    return builder()
        .status(status)
        .minutesToWait(minutesToWait)
        .rentConnection(rentConnection)
        .rentMateProposal(rentMateProposal)
        .build();
  }

  public static InitiatorCurrentState with(InitiatorStatus status, RentConnection rentConnection, RentMateProposal rentMateProposal) {
    return builder()
        .status(status)
        .rentConnection(rentConnection)
        .rentMateProposal(rentMateProposal)
        .build();
  }
}
