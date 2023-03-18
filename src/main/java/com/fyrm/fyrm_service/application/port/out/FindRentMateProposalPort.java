package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.RentMateProposal;
import java.util.List;
import java.util.Optional;

public interface FindRentMateProposalPort {

  Optional<RentMateProposal> findByRentConnectionId(Long rentConnectionId);

  List<Long> findProposedUserIdsForRentConnections(List<Long> rentConnectionIds);
}
