package com.fyrm.fyrm_service.adapters.out.stomp.converter;

import com.fyrm.fyrm_service.adapters.out.stomp.dto.StompChatMessage;
import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompChatMessageConverter implements Converter<ChatMessage, StompChatMessage> {

  @Override
  public StompChatMessage apply(ChatMessage chatMessage) {
    if (chatMessage == null) {
      throw new IllegalArgumentException("Chat message must not be null for converting to stomp chat message");
    }

    return StompChatMessage.builder()
        .id(chatMessage.getId())
        .content(chatMessage.getContent())
        .fromId(chatMessage.getFromId())
        .fromUsername(chatMessage.getFromUsername())
        .toId(chatMessage.getToId())
        .toUsername(chatMessage.getToUsername())
        .sentAtHoursMinutesFormat(toHoursMinutesFormat(chatMessage.getSentAt()))
        .sentOnDayMonthYearFormat(toDayMonthYearFormat(chatMessage.getSentAt()))
        .build();
  }

  private String toHoursMinutesFormat(ZonedDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("hh:mm"));
  }

  private String toDayMonthYearFormat(ZonedDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
