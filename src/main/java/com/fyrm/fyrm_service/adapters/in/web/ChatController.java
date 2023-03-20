package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.application.port.in.command.SendMessageCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.SendMessageUseCase;
import com.fyrm.fyrm_service.generatedapi.dtos.ChatMessageDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class ChatController {

  private final SendMessageUseCase sendMessageUseCase;

  @MessageMapping("/private-message")
  public void sendMessage(@Payload ChatMessageDto message, Principal from) {
    var command = new SendMessageCommand(message.getContent(), message.getFromId(), from.getName(), message.getToId(), message.getToUsername());
    sendMessageUseCase.send(command);
  }
}
