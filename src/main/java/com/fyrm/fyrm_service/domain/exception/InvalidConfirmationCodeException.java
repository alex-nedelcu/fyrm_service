package com.fyrm.fyrm_service.domain.exception;

import static com.fyrm.fyrm_service.domain.exception.base.BusinessExceptionType.CONFIRMATION_CODE_NOT_VALID;

import com.fyrm.fyrm_service.domain.exception.base.BusinessException;
import java.util.List;

public class InvalidConfirmationCodeException extends BusinessException {

  public InvalidConfirmationCodeException() {
    super(CONFIRMATION_CODE_NOT_VALID, "error.exception.confirmation.code.not.valid", null);
  }

  public InvalidConfirmationCodeException(String message) {
    super(CONFIRMATION_CODE_NOT_VALID, message, null);
  }

  public InvalidConfirmationCodeException(List<String> params) {
    super(CONFIRMATION_CODE_NOT_VALID, "error.exception.confirmation.code.not.valid", params);
  }
}
