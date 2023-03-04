package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.UpdateRentConnectionCommand;

public interface UpdateRentConnectionUseCase {

  void update(UpdateRentConnectionCommand updateRentConnectionCommand);
}
