package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.RentMateProposal;

public interface PersistRentMateProposalPort {

  Long persist(RentMateProposal rentMateProposal);
}
