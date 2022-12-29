package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.GetAccountsByNameCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.GetAccountsByNameUseCase;
import com.fyrm.fyrm_service.application.port.out.FindAccountPort;
import com.fyrm.fyrm_service.domain.Account;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetAccountsByNameService implements GetAccountsByNameUseCase {

  private final FindAccountPort findAccountPort;

  @Override
  public List<Account> get(GetAccountsByNameCommand getAccountsByNameCommand) {
    return findAccountPort.findAllByName(getAccountsByNameCommand.getName());
  }
}
