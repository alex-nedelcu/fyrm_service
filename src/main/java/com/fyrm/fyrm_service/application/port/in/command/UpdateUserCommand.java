package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {

  @NotNull(message = "error.validation.user.id.is.mandatory")
  Long userId;

  String description;

  @NotNull(message = "error.validation.is.searching.is.mandatory")
  Boolean isSearching;

  public UpdateUserCommand(Long userId, String description, Boolean isSearching) {
    this.userId = userId;
    this.description = description;
    this.isSearching = isSearching;
    validateSelf();
  }
}
