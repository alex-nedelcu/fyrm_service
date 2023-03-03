package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.RentConnectionEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentConnectionRepository extends BaseRepository<RentConnectionEntity, Long> {

}