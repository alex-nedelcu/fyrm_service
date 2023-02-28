package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.UpdateSearchProfileCommand;

public interface UpdateSearchProfileUseCase {

  void update(UpdateSearchProfileCommand updateSearchProfileCommand);
}
