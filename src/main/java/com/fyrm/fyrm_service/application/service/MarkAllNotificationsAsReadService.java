package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.MarkAllNotificationsAsReadCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.MarkAllNotificationsAsReadUseCase;
import com.fyrm.fyrm_service.application.port.out.FindNotificationPort;
import com.fyrm.fyrm_service.application.port.out.PersistNotificationPort;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class MarkAllNotificationsAsReadService implements MarkAllNotificationsAsReadUseCase {

  private final FindNotificationPort findNotificationPort;
  private final PersistNotificationPort persistNotificationPort;

  @Override
  public void markAll(MarkAllNotificationsAsReadCommand markAllNotificationsAsReadCommand) {
    var notifications = findNotificationPort.findAllReceivedBy(markAllNotificationsAsReadCommand.getUserId());
    notifications.forEach(notification -> persistNotificationPort.persist(notification.deepCloneWithIsRead(true)));
  }
}
