package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.SendConfirmationCodeByEmailCommand;

public interface SendConfirmationCodeByEmailUseCase {

  void sendByEmail(SendConfirmationCodeByEmailCommand sendConfirmationCodeByEmailCommand);
}
