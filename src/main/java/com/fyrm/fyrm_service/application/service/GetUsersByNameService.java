package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.GetUsersByNameCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.GetUsersByNameUseCase;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.domain.User;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetUsersByNameService implements GetUsersByNameUseCase {

  private final FindUserPort findUserPort;

  @Override
  public List<User> get(GetUsersByNameCommand getUsersByNameCommand) {
    return findUserPort.findAllByName(getUsersByNameCommand.getName());
  }
}
