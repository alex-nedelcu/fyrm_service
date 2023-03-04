package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.UpdateRentConnectionCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.UpdateRentConnectionUseCase;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateRentConnectionService implements UpdateRentConnectionUseCase {

  @Override
  public void update(UpdateRentConnectionCommand updateRentConnectionCommand) {

  }
}
