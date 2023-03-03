package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.SearchProfileEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchProfileRepository extends BaseRepository<SearchProfileEntity, Long> {

  List<SearchProfileEntity> findAllByUserId(Long userId);
}