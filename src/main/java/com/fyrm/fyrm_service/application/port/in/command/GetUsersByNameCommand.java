package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class GetUsersByNameCommand extends SelfValidating<GetUsersByNameCommand> {

  @NotBlank(message = "error.validation.user.name.is.mandatory")
  String name;

  public GetUsersByNameCommand(String name) {
    this.name = name;
    validateSelf();
  }
}
