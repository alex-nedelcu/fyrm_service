package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.mapper.ChatMessageMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.ChatMessageRepository;
import com.fyrm.fyrm_service.application.port.out.FindMessagePort;
import com.fyrm.fyrm_service.application.port.out.PersistMessagePort;
import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class ChatMessageAdapter implements PersistMessagePort, FindMessagePort {

  private final ChatMessageRepository chatMessageRepository;
  private final ChatMessageMapper chatMessageMapper;

  @Override
  public void persist(ChatMessage chatMessage) {
    chatMessageRepository.save(chatMessageMapper.toEntity(chatMessage));
  }

  @Override
  public List<ChatMessage> findAllSentOrReceivedBy(Long userId) {
    return chatMessageMapper.toDomainList(chatMessageRepository.findAllByFromIdIsOrToIdIsOrderBySentAtAsc(userId, userId));
  }
}
