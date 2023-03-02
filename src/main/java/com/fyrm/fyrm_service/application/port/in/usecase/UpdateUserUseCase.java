package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.UpdateUserCommand;

public interface UpdateUserUseCase {

  void update(UpdateUserCommand updateUserCommand);
}
