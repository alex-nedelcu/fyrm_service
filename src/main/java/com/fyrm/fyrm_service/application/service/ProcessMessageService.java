package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ProcessMessageCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ProcessMessageUseCase;
import com.fyrm.fyrm_service.application.port.out.PersistMessagePort;
import com.fyrm.fyrm_service.application.port.out.PersistNotificationPort;
import com.fyrm.fyrm_service.application.port.out.SendMessagePort;
import com.fyrm.fyrm_service.application.port.out.SendNotificationPort;
import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.domain.Notification;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ProcessMessageService implements ProcessMessageUseCase {

  private static final String NEW_CHAT_MESSAGE_NOTIFICATION_PREVIEW = "Click here to see the new message from ";
  private final SendMessagePort sendMessagePort;
  private final PersistMessagePort persistMessagePort;
  private final SendNotificationPort sendNotificationPort;
  private final PersistNotificationPort persistNotificationPort;

  @Override
  @Transactional
  public void process(ProcessMessageCommand processMessageCommand) {
    var message = buildMessage(processMessageCommand);
    var notification = buildNotification(processMessageCommand);

    sendMessagePort.sendToReceiverAndSender(message);
    persistMessagePort.persist(message);

    var savedNotification = persistNotificationPort.persist(notification);
    sendNotificationPort.send(savedNotification);
  }

  private ChatMessage buildMessage(ProcessMessageCommand command) {
    return ChatMessage.builder()
        .fromId(command.getFromId())
        .fromUsername(command.getFromUsername())
        .toId(command.getToId())
        .toUsername(command.getToUsername())
        .content(command.getContent())
        .sentAt(ZonedDateTime.now())
        .build();
  }

  private Notification buildNotification(ProcessMessageCommand command) {
    return Notification.builder()
        .fromId(command.getFromId())
        .fromUsername(command.getFromUsername())
        .toId(command.getToId())
        .toUsername(command.getToUsername())
        .preview(NEW_CHAT_MESSAGE_NOTIFICATION_PREVIEW + command.getFromUsername())
        .sentAt(ZonedDateTime.now())
        .build();
  }
}
