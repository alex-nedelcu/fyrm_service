package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.RentMateProposalEntity;
import com.fyrm.fyrm_service.domain.RentMateProposal;
import lombok.RequiredArgsConstructor;

@DomainEntityMapper
@RequiredArgsConstructor
public class RentMateProposalMapper implements IDomainEntityMapper<RentMateProposalEntity, RentMateProposal> {

  private final ProposedRentMateMapper proposedRentMateMapper;

  @Override
  public RentMateProposalEntity toEntity(RentMateProposal rentMateProposal) {
    return RentMateProposalEntity.builder()
        .id(rentMateProposal.getId())
        .rentConnectionId(rentMateProposal.getRentConnectionId())
        .proposedRentMates(proposedRentMateMapper.toEntityList(rentMateProposal.getProposedRentMates()))
        .build();
  }

  @Override
  public RentMateProposal toDomain(RentMateProposalEntity rentMateProposalEntity) {
    return RentMateProposal.builder()
        .id(rentMateProposalEntity.getId())
        .rentConnectionId(rentMateProposalEntity.getRentConnectionId())
        .proposedRentMates(proposedRentMateMapper.toDomainList(rentMateProposalEntity.getProposedRentMates()))
        .build();
  }
}
