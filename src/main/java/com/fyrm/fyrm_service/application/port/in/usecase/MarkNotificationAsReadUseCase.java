package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.MarkNotificationAsReadCommand;

public interface MarkNotificationAsReadUseCase {

  void mark(MarkNotificationAsReadCommand markNotificationAsReadCommand);
}
