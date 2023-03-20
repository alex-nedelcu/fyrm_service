package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.SendMessageCommand;

public interface SendMessageUseCase {

  void send(SendMessageCommand sendMessageCommand);
}
