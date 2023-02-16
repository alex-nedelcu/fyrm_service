package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.SearchProfileEntity;
import com.fyrm.fyrm_service.adapters.out.persistence.mapper.SearchProfileMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.SearchProfileRepository;
import com.fyrm.fyrm_service.application.port.out.PersistSearchProfilePort;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class SearchProfileAdapter implements PersistSearchProfilePort {

  private final SearchProfileRepository searchProfileRepository;
  private final SearchProfileMapper searchProfileMapper;

  @Override
  public SearchProfile persist(SearchProfile searchProfile) {
    SearchProfileEntity entity = searchProfileMapper.toEntity(searchProfile);
    SearchProfileEntity savedEntity = searchProfileRepository.save(entity);
    return searchProfileMapper.toDomain(savedEntity);
  }
}
