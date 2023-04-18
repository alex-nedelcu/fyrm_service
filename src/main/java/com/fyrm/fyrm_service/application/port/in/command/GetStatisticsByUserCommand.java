package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = false)
public class GetStatisticsByUserCommand extends SelfValidating<GetStatisticsByUserCommand> {

  @NotNull(message = "error.validation.user.id.is.mandatory")
  Long userId;

  public GetStatisticsByUserCommand(Long userId) {
    this.userId = userId;
    validateSelf();
  }
}
