package com.fyrm.fyrm_service.adapters.in.web.converter.chat;

import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.generatedapi.dtos.FetchedChatMessageDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class FetchedChatMessageConverter implements Converter<ChatMessage, FetchedChatMessageDto> {

  @Override
  public FetchedChatMessageDto apply(ChatMessage message) {
    if (message == null) {
      throw new IllegalArgumentException("Message must not be null for converting to dto");
    }

    return new FetchedChatMessageDto()
        .id(message.getId())
        .content(message.getContent())
        .fromId(message.getFromId())
        .fromUsername(message.getFromUsername())
        .toId(message.getToId())
        .toUsername(message.getToUsername())
        .sentAtHoursMinutesFormat(toHoursMinutesFormat(message.getSentAt()))
        .sentOnDayMonthYearFormat(toDayMonthYearFormat(message.getSentAt()));
  }

  private String toHoursMinutesFormat(ZonedDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("hh:mm"));
  }

  private String toDayMonthYearFormat(ZonedDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
