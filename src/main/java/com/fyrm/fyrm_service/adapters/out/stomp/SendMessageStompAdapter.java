package com.fyrm.fyrm_service.adapters.out.stomp;

import com.fyrm.fyrm_service.adapters.out.stomp.converter.StompChatMessageConverter;
import com.fyrm.fyrm_service.application.port.out.SendMessagePort;
import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.web_socket.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@OutboundAdapter
@RequiredArgsConstructor
public class SendMessageStompAdapter implements SendMessagePort {

  private final SimpMessagingTemplate messagingTemplate;
  private final StompChatMessageConverter stompChatMessageConverter;

  @Override
  public void send(ChatMessage chatMessage) {
    messagingTemplate.convertAndSendToUser(
        chatMessage.getToUsername(),
        Topic.PRIVATE_MESSAGES.getName(),
        stompChatMessageConverter.apply(chatMessage)
    );
  }
}
