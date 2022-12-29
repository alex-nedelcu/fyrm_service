package com.fyrm.fyrm_service.domain.exception;

import static com.fyrm.fyrm_service.domain.exception.base.BusinessExceptionType.SIGNUP_INFORMATION_NOT_VALID;

import com.fyrm.fyrm_service.domain.exception.base.BusinessException;
import java.util.List;

public class InvalidSignupInformationException extends BusinessException {

  public InvalidSignupInformationException() {
    super(SIGNUP_INFORMATION_NOT_VALID, "error.exception.signup.information.not.valid", null);
  }

  public InvalidSignupInformationException(String message) {
    super(SIGNUP_INFORMATION_NOT_VALID, message, null);
  }

  public InvalidSignupInformationException(List<String> params) {
    super(SIGNUP_INFORMATION_NOT_VALID, "error.exception.signup.information.not.valid", params);
  }
}
