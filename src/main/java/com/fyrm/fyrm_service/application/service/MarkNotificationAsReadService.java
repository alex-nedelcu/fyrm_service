package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.MarkNotificationAsReadCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.MarkNotificationAsReadUseCase;
import com.fyrm.fyrm_service.application.port.out.FindNotificationPort;
import com.fyrm.fyrm_service.application.port.out.PersistNotificationPort;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class MarkNotificationAsReadService implements MarkNotificationAsReadUseCase {

  private final FindNotificationPort findNotificationPort;
  private final PersistNotificationPort persistNotificationPort;

  @Override
  public void mark(MarkNotificationAsReadCommand markNotificationAsReadCommand) {
    var notification = findNotificationPort.findById(markNotificationAsReadCommand.getNotificationId()).orElseThrow(ResourceNotFoundException::new);
    persistNotificationPort.persist(notification.deepCloneWithIsRead(true));
  }
}
