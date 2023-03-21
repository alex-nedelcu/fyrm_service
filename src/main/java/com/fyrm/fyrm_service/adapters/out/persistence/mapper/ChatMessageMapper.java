package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.ChatMessageEntity;
import com.fyrm.fyrm_service.domain.ChatMessage;

@DomainEntityMapper
public class ChatMessageMapper implements IDomainEntityMapper<ChatMessageEntity, ChatMessage> {

  @Override
  public ChatMessageEntity toEntity(ChatMessage chatMessage) {
    return ChatMessageEntity.builder()
        .id(chatMessage.getId())
        .content(chatMessage.getContent())
        .fromId(chatMessage.getFromId())
        .toId(chatMessage.getToId())
        .fromUsername(chatMessage.getFromUsername())
        .toUsername(chatMessage.getToUsername())
        .sentAt(chatMessage.getSentAt())
        .build();
  }

  @Override
  public ChatMessage toDomain(ChatMessageEntity chatMessageEntity) {
    return ChatMessage.builder()
        .id(chatMessageEntity.getId())
        .content(chatMessageEntity.getContent())
        .fromId(chatMessageEntity.getFromId())
        .toId(chatMessageEntity.getToId())
        .fromUsername(chatMessageEntity.getFromUsername())
        .toUsername(chatMessageEntity.getToUsername())
        .sentAt(chatMessageEntity.getSentAt())
        .build();
  }
}
