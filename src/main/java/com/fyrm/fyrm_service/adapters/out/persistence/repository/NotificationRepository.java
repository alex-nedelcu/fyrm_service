package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.NotificationEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends BaseRepository<NotificationEntity, Long> {

  List<NotificationEntity> findAllByToIdIsOrderBySentAtAsc(Long userId);
}
