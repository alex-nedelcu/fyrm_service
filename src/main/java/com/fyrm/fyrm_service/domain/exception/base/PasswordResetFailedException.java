package com.fyrm.fyrm_service.domain.exception.base;

import java.util.List;

import static com.fyrm.fyrm_service.domain.exception.base.BusinessExceptionType.PASSWORD_RESET_FAILED;

public class PasswordResetFailedException extends BusinessException {

  public PasswordResetFailedException() {
    super(PASSWORD_RESET_FAILED, "error.exception.password.reset.failed", null);
  }

  public PasswordResetFailedException(String message) {
    super(PASSWORD_RESET_FAILED, message, null);
  }

  public PasswordResetFailedException(List<String> params) {
    super(PASSWORD_RESET_FAILED, "error.exception.password.reset.failed", params);
  }
}
