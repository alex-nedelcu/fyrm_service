package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class GetAccountsByNameCommand extends SelfValidating<GetAccountsByNameCommand> {

  @NotBlank(message = "error.validation.account.name.is.mandatory")
  String name;

  public GetAccountsByNameCommand(String name) {
    this.name = name;
    validateSelf();
  }
}
