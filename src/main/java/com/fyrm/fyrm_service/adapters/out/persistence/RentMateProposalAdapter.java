package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.mapper.RentMateProposalMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.RentMateProposalRepository;
import com.fyrm.fyrm_service.application.port.out.PersistRentMateProposalPort;
import com.fyrm.fyrm_service.domain.RentMateProposal;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class RentMateProposalAdapter implements PersistRentMateProposalPort {

  private final RentMateProposalRepository rentMateProposalRepository;
  private final RentMateProposalMapper rentMateProposalMapper;

  @Override
  public Long persist(RentMateProposal rentMateProposal) {
    var entity = rentMateProposalRepository.save(rentMateProposalMapper.toEntity(rentMateProposal));
    return entity.getId();
  }
}