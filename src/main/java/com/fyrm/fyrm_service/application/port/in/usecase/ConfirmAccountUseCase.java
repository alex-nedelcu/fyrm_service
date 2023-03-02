package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.ConfirmAccountCommand;

public interface ConfirmAccountUseCase {

  void confirm(ConfirmAccountCommand confirmAccountCommand);
}
