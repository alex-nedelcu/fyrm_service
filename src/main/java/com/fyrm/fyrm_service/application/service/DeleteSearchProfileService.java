package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.DeleteSearchProfileCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.DeleteSearchProfileUseCase;
import com.fyrm.fyrm_service.application.port.out.DeleteSearchProfilePort;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteSearchProfileService implements DeleteSearchProfileUseCase {

  private final DeleteSearchProfilePort deleteSearchProfilePort;

  @Override
  public void delete(DeleteSearchProfileCommand deleteSearchProfileCommand) {
    deleteSearchProfilePort.delete(deleteSearchProfileCommand.getId());
  }
}
