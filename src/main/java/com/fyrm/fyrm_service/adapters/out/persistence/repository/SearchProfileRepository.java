package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.SearchProfileEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchProfileRepository extends BaseRepository<SearchProfileEntity, Long> {

  List<SearchProfileEntity> findAllByUser_IdOrderByIdAsc(Long userId);

  List<SearchProfileEntity> findAllByUser_IdIn(List<Long> userIds);
}
