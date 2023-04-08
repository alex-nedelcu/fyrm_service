package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.FindNotificationsReceivedByUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.FindNotificationsReceivedByUserUseCase;
import com.fyrm.fyrm_service.application.port.out.FindNotificationPort;
import com.fyrm.fyrm_service.domain.Notification;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindNotificationsReceivedByUserService implements FindNotificationsReceivedByUserUseCase {

  private final FindNotificationPort findNotificationPort;

  @Override
  public List<Notification> find(FindNotificationsReceivedByUserCommand findNotificationsReceivedByUserCommand) {
    return findNotificationPort.findAllReceivedBy(findNotificationsReceivedByUserCommand.getUserId());
  }
}
