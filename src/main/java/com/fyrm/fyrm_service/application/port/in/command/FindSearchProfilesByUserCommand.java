package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class FindSearchProfilesByUserCommand extends SelfValidating<FindSearchProfilesByUserCommand> {

  @NotNull(message = "error.validation.user.id.is.mandatory")
  Long userId;

  public FindSearchProfilesByUserCommand(Long userId) {
    this.userId = userId;
    validateSelf();
  }
}
