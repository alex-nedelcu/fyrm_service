package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.MarkAllNotificationsAsReadCommand;

public interface MarkAllNotificationsAsReadUseCase {

  void markAll(MarkAllNotificationsAsReadCommand markAllNotificationsAsReadCommand);
}
