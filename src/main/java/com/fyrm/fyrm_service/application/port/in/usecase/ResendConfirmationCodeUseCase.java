package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.ResendConfirmationCodeCommand;

public interface ResendConfirmationCodeUseCase {

  void resend(ResendConfirmationCodeCommand resendConfirmationCodeCommand);
}
