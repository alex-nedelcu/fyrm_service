package com.fyrm.fyrm_service.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentMateProposal {

  Long id;
  Long rentConnectionId;
  List<ProposedRentMate> proposedRentMates;
}
