package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.RentConnectionEntity;
import com.fyrm.fyrm_service.domain.RentConnection;
import lombok.RequiredArgsConstructor;

@DomainEntityMapper
@RequiredArgsConstructor
public class RentConnectionMapper implements IDomainEntityMapper<RentConnectionEntity, RentConnection> {

  private final SearchProfileMapper searchProfileMapper;

  @Override
  public RentConnectionEntity toEntity(RentConnection rentConnection) {
    return RentConnectionEntity.builder()
        .id(rentConnection.getId())
        .initiatorId(rentConnection.getInitiatorId())
        .proposalMaximumSize(rentConnection.getProposalMaximumSize())
        .status(rentConnection.getStatus())
        .searchProfiles(searchProfileMapper.toEntityList(rentConnection.getUsedSearchProfiles()))
        .build();
  }

  @Override
  public RentConnection toDomain(RentConnectionEntity rentConnectionEntity) {
    return RentConnection.builder()
        .id(rentConnectionEntity.getId())
        .initiatorId(rentConnectionEntity.getInitiatorId())
        .proposalMaximumSize(rentConnectionEntity.getProposalMaximumSize())
        .status(rentConnectionEntity.getStatus())
        .usedSearchProfiles(searchProfileMapper.toDomainList(rentConnectionEntity.getSearchProfiles()))
        .build();
  }
}
