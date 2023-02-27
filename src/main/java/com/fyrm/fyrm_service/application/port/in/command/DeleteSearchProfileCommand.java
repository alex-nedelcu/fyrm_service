package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class DeleteSearchProfileCommand extends SelfValidating<DeleteSearchProfileCommand> {

  @NotNull(message = "error.validation.search.profile.id.is.mandatory")
  Long id;

  public DeleteSearchProfileCommand(Long id) {
    this.id = id;
    validateSelf();
  }
}
