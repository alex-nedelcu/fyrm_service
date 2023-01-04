package com.fyrm.fyrm_service.adapters.in.web.converter;

import com.fyrm.fyrm_service.domain.Account;
import com.fyrm.fyrm_service.generatedapi.dtos.AccountDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountDtoConverter implements Converter<Account, AccountDto> {

  @Override
  public AccountDto apply(Account account) {

    if (account == null) {
      throw new IllegalArgumentException("Account must not be null for converting to dto!");
    }

    return new AccountDto()
        .id(account.getId())
        .name(account.getName());
  }
}
