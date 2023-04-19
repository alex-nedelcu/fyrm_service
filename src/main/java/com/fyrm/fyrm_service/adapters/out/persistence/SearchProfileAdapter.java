package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.SearchProfileEntity;
import com.fyrm.fyrm_service.adapters.out.persistence.mapper.SearchProfileMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.SearchProfileRepository;
import com.fyrm.fyrm_service.application.port.out.DeleteSearchProfilePort;
import com.fyrm.fyrm_service.application.port.out.FindSearchProfilePort;
import com.fyrm.fyrm_service.application.port.out.PersistSearchProfilePort;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@OutboundAdapter
@RequiredArgsConstructor
public class SearchProfileAdapter implements PersistSearchProfilePort, FindSearchProfilePort, DeleteSearchProfilePort {

  private final SearchProfileRepository searchProfileRepository;
  private final SearchProfileMapper searchProfileMapper;

  @Override
  public SearchProfile persist(SearchProfile searchProfile) {
    SearchProfileEntity entity = searchProfileMapper.toEntity(searchProfile);
    SearchProfileEntity savedEntity = searchProfileRepository.save(entity);
    return searchProfileMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<SearchProfile> findById(Long id) {
    Optional<SearchProfileEntity> optionalEntity = searchProfileRepository.findById(id);
    return optionalEntity.map(searchProfileMapper::toDomain);
  }

  @Override
  public List<SearchProfile> findAllByUserId(Long userId) {
    List<SearchProfileEntity> entities = searchProfileRepository.findAllByUser_IdOrderByIdAsc(userId);
    return searchProfileMapper.toDomainList(entities);
  }

  @Override
  public List<SearchProfile> findAllByIds(List<Long> ids) {
    List<SearchProfileEntity> entities = searchProfileRepository.findAllById(ids);
    return searchProfileMapper.toDomainList(entities);
  }

  @Override
  public List<SearchProfile> findAllByUsers(List<User> users) {
    List<Long> userIds = users.stream().map(User::getId).toList();
    List<SearchProfileEntity> entities = searchProfileRepository.findAllByUser_IdIn(userIds);
    return searchProfileMapper.toDomainList(entities);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    searchProfileRepository.deleteById(id);
  }
}
