package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.RentConnectionEntity;
import com.fyrm.fyrm_service.domain.RentConnectionStatus;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RentConnectionRepository extends BaseRepository<RentConnectionEntity, Long> {

  Optional<RentConnectionEntity> findFirstByInitiatorIdAndStatusOrderByCreatedAtDesc(Long initiatorId, RentConnectionStatus status);

  List<RentConnectionEntity> findAllByInitiatorId(Long initiatorId);

  List<RentConnectionEntity> findAllByInitiatorIdAndStatusAndCreatedAtAfter(Long initiatorId, RentConnectionStatus status, ZonedDateTime after);
}
