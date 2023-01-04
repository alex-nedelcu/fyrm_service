package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class ConfirmAccountCommand extends SelfValidating<ConfirmAccountCommand> {

  @NotBlank(message = "error.validation.code.is.mandatory")
  String code;

  public ConfirmAccountCommand(String code) {
    this.code = code;
    validateSelf();
  }
}
