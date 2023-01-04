package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class LoginUserCommand extends SelfValidating<LoginUserCommand> {

  @NotNull(message = "error.validation.username.is.mandatory")
  String username;

  @NotNull(message = "error.validation.password.is.mandatory")
  String password;

  public LoginUserCommand(String username, String password) {
    this.username = username;
    this.password = password;
    validateSelf();
  }
}
