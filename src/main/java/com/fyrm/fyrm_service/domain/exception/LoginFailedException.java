package com.fyrm.fyrm_service.domain.exception;

import static com.fyrm.fyrm_service.domain.exception.base.BusinessExceptionType.LOGIN_FAILED;

import com.fyrm.fyrm_service.domain.exception.base.BusinessException;
import java.util.List;

public class LoginFailedException extends BusinessException {

  public LoginFailedException() {
    super(LOGIN_FAILED, "error.exception.login.failed", null);
  }

  public LoginFailedException(String message) {
    super(LOGIN_FAILED, message, null);
  }

  public LoginFailedException(List<String> params) {
    super(LOGIN_FAILED, "error.exception.login.failed", params);
  }
}
