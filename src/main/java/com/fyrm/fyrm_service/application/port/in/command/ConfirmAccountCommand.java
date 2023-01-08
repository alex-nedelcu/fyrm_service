package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class ConfirmAccountCommand extends SelfValidating<ConfirmAccountCommand> {

  @NotNull(message = "error.validation.user.id.is.mandatory")
  Long userId;
  @NotBlank(message = "error.validation.code.is.mandatory")
  String code;

  public ConfirmAccountCommand(Long userId, String code) {
    this.userId = userId;
    this.code = code;
    validateSelf();
  }
}