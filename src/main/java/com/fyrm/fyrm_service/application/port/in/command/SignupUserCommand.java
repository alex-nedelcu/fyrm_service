package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class SignupUserCommand extends SelfValidating<SignupUserCommand> {

  private static final int MINIMUM_LENGTH = 3;
  private static final int USERNAME_MAXIMUM_LENGTH = 32;
  private static final int EMAIL_MAXIMUM_LENGTH = 64;
  private static final int PASSWORD_MAXIMUM_LENGTH = 128;
  private static final int ROLE_MAXIMUM_LENGTH = 32;

  @NotBlank(message = "error.validation.username.is.mandatory")
  @Size(min = MINIMUM_LENGTH, max = USERNAME_MAXIMUM_LENGTH, message = "error.validation.username.size.is.not.valid")
  String username;

  @NotBlank(message = "error.validation.email.is.mandatory")
  @Size(min = MINIMUM_LENGTH, max = EMAIL_MAXIMUM_LENGTH, message = "error.validation.email.size.is.not.valid")
  String email;

  @NotBlank(message = "error.validation.password.is.mandatory")
  @Size(min = MINIMUM_LENGTH, max = PASSWORD_MAXIMUM_LENGTH, message = "error.validation.password.size.is.not.valid")
  String password;

  @NotBlank(message = "error.validation.role.is.mandatory")
  @Size(min = MINIMUM_LENGTH, max = ROLE_MAXIMUM_LENGTH, message = "error.validation.role.size.is.not.valid")
  String role;

  public SignupUserCommand(String username, String email, String password, String role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    validateSelf();
  }
}
