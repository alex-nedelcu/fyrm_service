package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.UpdateRentConnectionCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.UpdateRentConnectionUseCase;
import com.fyrm.fyrm_service.application.port.out.FindRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentConnectionPort;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateRentConnectionService implements UpdateRentConnectionUseCase {

  private final FindRentConnectionPort findRentConnectionPort;
  private final PersistRentConnectionPort persistRentConnectionPort;

  @Override
  public void update(UpdateRentConnectionCommand updateRentConnectionCommand) {
    Long rentConnectionId = updateRentConnectionCommand.getRentConnectionId();
    RentConnection rentConnection = findRentConnectionPort.findById(rentConnectionId).orElseThrow(
        () -> new ResourceNotFoundException("Rent connection with id " + rentConnectionId + " not found")
    );

    rentConnection.setStatus(updateRentConnectionCommand.getStatus());
    persistRentConnectionPort.persist(rentConnection);
  }
}
