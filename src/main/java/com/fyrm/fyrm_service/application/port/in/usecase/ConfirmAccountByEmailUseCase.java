package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.ConfirmAccountByEmailCommand;

public interface ConfirmAccountByEmailUseCase {

  void confirm(ConfirmAccountByEmailCommand confirmAccountByEmailCommand);
}
