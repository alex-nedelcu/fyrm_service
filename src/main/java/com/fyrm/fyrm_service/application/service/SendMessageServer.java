package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.SendMessageCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.SendMessageUseCase;
import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.web_socket.Topic;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@UseCase
@RequiredArgsConstructor
public class SendMessageServer implements SendMessageUseCase {

  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public void send(SendMessageCommand sendMessageCommand) {
    messagingTemplate.convertAndSendToUser(
        sendMessageCommand.getToUsername(),
        Topic.PRIVATE_MESSAGES.getName(),
        ChatMessage.builder()
            .fromId(sendMessageCommand.getFromId())
            .fromUsername(sendMessageCommand.getFromUsername())
            .toId(sendMessageCommand.getToId())
            .toUsername(sendMessageCommand.getToUsername())
            .content(sendMessageCommand.getContent())
            .sentAt(ZonedDateTime.now())
            .build()
    );

    // TODO: persist message to database
  }
}
