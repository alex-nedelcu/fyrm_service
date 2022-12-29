package com.fyrm.fyrm_service.application.port.in.usecasse;

import com.fyrm.fyrm_service.application.port.in.command.GetAccountsByNameCommand;
import com.fyrm.fyrm_service.domain.Account;
import java.util.List;

public interface GetAccountsByNameUseCase {

  List<Account> get(GetAccountsByNameCommand getAccountsByNameCommand);
}
