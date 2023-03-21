package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.adapters.in.web.converter.chat.ChatMessagesByUserConverter;
import com.fyrm.fyrm_service.application.port.in.command.FindUserChatMessagesCommand;
import com.fyrm.fyrm_service.application.port.in.command.ProcessMessageCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.FindUserChatMessagesUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.ProcessMessageUseCase;
import com.fyrm.fyrm_service.generatedapi.ChatApi;
import com.fyrm.fyrm_service.generatedapi.dtos.ChatMessagesByUserDto;
import com.fyrm.fyrm_service.generatedapi.dtos.ReceivedChatMessageDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class ChatController implements ChatApi {

  private final ProcessMessageUseCase processMessageUseCase;
  private final FindUserChatMessagesUseCase findUserChatMessagesUseCase;
  private final ChatMessagesByUserConverter chatMessagesByUserConverter;

  @MessageMapping("/private-message")
  public void processMessage(@Payload ReceivedChatMessageDto message, Principal from) {
    var command = new ProcessMessageCommand(
        message.getContent(),
        message.getFromId(),
        from.getName(),
        message.getToId(),
        message.getToUsername()
    );
    processMessageUseCase.process(command);
  }

  @Override
  public ResponseEntity<ChatMessagesByUserDto> findChatMessagesByUser(Long userId) {
    var command = new FindUserChatMessagesCommand(userId);
    var messages = findUserChatMessagesUseCase.find(command);
    return ResponseEntity.ok(chatMessagesByUserConverter.apply(messages));
  }
}
