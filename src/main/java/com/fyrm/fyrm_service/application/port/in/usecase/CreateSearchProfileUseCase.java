package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.CreateSearchProfileCommand;

public interface CreateSearchProfileUseCase {

  void create(CreateSearchProfileCommand createSearchProfileCommand);
}
