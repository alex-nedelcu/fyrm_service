package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.ResetPasswordCommand;

public interface ResetPasswordUseCase {

  void reset(ResetPasswordCommand resetPasswordCommand);
}
