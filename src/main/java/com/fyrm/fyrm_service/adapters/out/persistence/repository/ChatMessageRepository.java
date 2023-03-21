package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.ChatMessageEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends BaseRepository<ChatMessageEntity, Long> {

  List<ChatMessageEntity> findAllByFromIdIsOrToIdIs(Long fromId, Long toId);
}
