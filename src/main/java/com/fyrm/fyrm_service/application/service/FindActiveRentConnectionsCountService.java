package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.usecase.FindActiveRentConnectionsCountUseCase;
import com.fyrm.fyrm_service.application.port.out.FindRentConnectionPort;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindActiveRentConnectionsCountService implements FindActiveRentConnectionsCountUseCase {

  private final FindRentConnectionPort findRentConnectionPort;

  @Override
  public int find() {
    return findRentConnectionPort.findActiveRentConnectionsCount();
  }
}
