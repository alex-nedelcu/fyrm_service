package com.fyrm.fyrm_service.domain.exception;

import static com.fyrm.fyrm_service.domain.exception.base.BusinessExceptionType.EMAIL_SENDING_FAILED;

import com.fyrm.fyrm_service.domain.exception.base.BusinessException;
import java.util.List;

public class EmailSendingFailed extends BusinessException {

  public EmailSendingFailed() {
    super(EMAIL_SENDING_FAILED, "error.exception.email.sending.failed", null);
  }

  public EmailSendingFailed(String message) {
    super(EMAIL_SENDING_FAILED, message, null);
  }

  public EmailSendingFailed(List<String> params) {
    super(EMAIL_SENDING_FAILED, "error.exception.email.sending.failed", params);
  }
}
