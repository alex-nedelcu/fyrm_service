package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.ProposedRentMateEntity;
import com.fyrm.fyrm_service.domain.ProposedRentMate;
import lombok.RequiredArgsConstructor;

@DomainEntityMapper
@RequiredArgsConstructor
public class ProposedRentMateMapper implements IDomainEntityMapper<ProposedRentMateEntity, ProposedRentMate> {

  @Override
  public ProposedRentMateEntity toEntity(ProposedRentMate proposedRentMate) {
    return ProposedRentMateEntity.builder()
        .id(proposedRentMate.getId())
        .userId(proposedRentMate.getUserId())
        .rentConnectionId(proposedRentMate.getRentConnectionId())
        .email(proposedRentMate.getEmail())
        .username(proposedRentMate.getUsername())
        .description(proposedRentMate.getDescription())
        .build();
  }

  @Override
  public ProposedRentMate toDomain(ProposedRentMateEntity proposedRentMateEntity) {
    return ProposedRentMate.builder()
        .id(proposedRentMateEntity.getId())
        .userId(proposedRentMateEntity.getUserId())
        .rentConnectionId(proposedRentMateEntity.getRentConnectionId())
        .email(proposedRentMateEntity.getEmail())
        .username(proposedRentMateEntity.getUsername())
        .description(proposedRentMateEntity.getDescription())
        .build();
  }
}
