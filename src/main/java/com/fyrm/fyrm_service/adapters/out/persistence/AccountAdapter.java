package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.repository.AccountRepository;
import com.fyrm.fyrm_service.application.port.out.FindAccountPort;
import com.fyrm.fyrm_service.domain.Account;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class AccountAdapter implements FindAccountPort {

  private final AccountRepository accountRepository;

  @Override
  public List<Account> findAllByName(String name) {
    return accountRepository.findAllByNameContainsIgnoreCase(name)
        .stream()
        .map(accountEntity -> Account.builder().id(accountEntity.getId()).name(accountEntity.getName()).build())
        .toList();
  }
}
