package com.fyrm.fyrm_service.adapters.in.web.converter.chat;

import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.generatedapi.dtos.ChatMessagesByUserDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessagesByUserConverter implements Converter<List<ChatMessage>, ChatMessagesByUserDto> {

  private final FetchedChatMessageConverter fetchedChatMessageConverter;

  @Override
  public ChatMessagesByUserDto apply(List<ChatMessage> messages) {
    if (messages == null) {
      throw new IllegalArgumentException("Messages must not be null for converting to dto");
    }

    return new ChatMessagesByUserDto().messages(fetchedChatMessageConverter.toList(messages));
  }
}
