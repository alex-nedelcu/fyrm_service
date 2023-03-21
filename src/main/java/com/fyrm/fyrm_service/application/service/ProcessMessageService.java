package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ProcessMessageCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ProcessMessageUseCase;
import com.fyrm.fyrm_service.application.port.out.PersistMessagePort;
import com.fyrm.fyrm_service.application.port.out.SendMessagePort;
import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ProcessMessageService implements ProcessMessageUseCase {

  private final SendMessagePort sendMessagePort;
  private final PersistMessagePort persistMessagePort;

  @Override
  @Transactional
  public void process(ProcessMessageCommand processMessageCommand) {
    var message = ChatMessage.builder()
        .fromId(processMessageCommand.getFromId())
        .fromUsername(processMessageCommand.getFromUsername())
        .toId(processMessageCommand.getToId())
        .toUsername(processMessageCommand.getToUsername())
        .content(processMessageCommand.getContent())
        .sentAt(ZonedDateTime.now())
        .build();

    sendMessagePort.sendToReceiverAndSender(message);
    persistMessagePort.persist(message);
  }
}
