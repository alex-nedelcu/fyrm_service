package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = false)
public class ConfirmAccountByEmailCommand extends SelfValidating<ConfirmAccountByEmailCommand> {

  private static final int MINIMUM_LENGTH = 3;
  private static final int EMAIL_MAXIMUM_LENGTH = 64;
  private static final int PASSWORD_MAXIMUM_LENGTH = 128;

  @NotBlank(message = "error.validation.email.is.mandatory")
  @Email(message = "error.validation.email.format.is.not.valid")
  @Size(min = MINIMUM_LENGTH, max = EMAIL_MAXIMUM_LENGTH, message = "error.validation.email.size.is.not.valid")
  String email;

  @NotBlank(message = "error.validation.code.is.mandatory")
  String code;

  public ConfirmAccountByEmailCommand(String email, String code) {
    this.email = email;
    this.code = code;
    validateSelf();
  }
}
