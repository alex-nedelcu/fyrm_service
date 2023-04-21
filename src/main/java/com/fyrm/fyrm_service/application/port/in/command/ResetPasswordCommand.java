package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordCommand extends SelfValidating<ResetPasswordCommand> {

  private static final int MINIMUM_LENGTH = 3;
  private static final int EMAIL_MAXIMUM_LENGTH = 64;
  private static final int PASSWORD_MAXIMUM_LENGTH = 128;

  @NotBlank(message = "error.validation.email.is.mandatory")
  @Email(message = "error.validation.email.format.is.not.valid")
  @Size(min = MINIMUM_LENGTH, max = EMAIL_MAXIMUM_LENGTH, message = "error.validation.email.size.is.not.valid")
  String email;

  @NotBlank(message = "error.validation.password.is.mandatory")
  @Size(min = MINIMUM_LENGTH, max = PASSWORD_MAXIMUM_LENGTH, message = "error.validation.password.size.is.not.valid")
  String password;

  @NotBlank(message = "error.validation.code.is.mandatory")
  String code;


  public ResetPasswordCommand(String email, String password, String code) {
    this.email = email;
    this.password = password;
    this.code = code;
    validateSelf();
  }
}
