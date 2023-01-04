package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.adapters.in.web.converter.AccountDtoConverter;
import com.fyrm.fyrm_service.application.port.in.command.GetAccountsByNameCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.GetAccountsByNameUseCase;
import com.fyrm.fyrm_service.generatedapi.AccountApi;
import com.fyrm.fyrm_service.generatedapi.dtos.AccountDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class AccountApiController implements AccountApi {

  private final GetAccountsByNameUseCase getAccountsByNameUseCase;
  private final AccountDtoConverter accountDtoConverter;

  @Override
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<List<AccountDto>> getAccountsByName(String name) {
    var getAccountsByNameCommand = new GetAccountsByNameCommand(name);
    var accounts = getAccountsByNameUseCase.get(getAccountsByNameCommand);
    return ResponseEntity.ok(accounts.stream().map(accountDtoConverter).toList());
  }
}
