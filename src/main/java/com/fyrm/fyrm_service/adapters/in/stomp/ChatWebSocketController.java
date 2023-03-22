package com.fyrm.fyrm_service.adapters.in.stomp;

import com.fyrm.fyrm_service.application.port.in.command.ProcessMessageCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ProcessMessageUseCase;
import com.fyrm.fyrm_service.generatedapi.dtos.ReceivedChatMessageDto;
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
public class ChatWebSocketController {

  private final ProcessMessageUseCase processMessageUseCase;

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
}
