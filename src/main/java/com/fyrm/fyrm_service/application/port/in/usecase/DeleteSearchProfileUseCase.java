package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.DeleteSearchProfileCommand;

public interface DeleteSearchProfileUseCase {

  void delete(DeleteSearchProfileCommand deleteSearchProfileCommand);
}
