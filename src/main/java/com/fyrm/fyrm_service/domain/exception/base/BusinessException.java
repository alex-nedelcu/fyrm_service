package com.fyrm.fyrm_service.domain.exception.base;

import java.util.List;
import lombok.Getter;

public abstract class BusinessException extends RuntimeException {

  @Getter
  private final BusinessExceptionType type;
  @Getter
  private final String messageKey;
  @Getter
  private final List<String> params;

  protected BusinessException(BusinessExceptionType type, String messageKey, List<String> params) {
    super(messageKey);
    this.type = type;
    this.messageKey = messageKey;
    this.params = params;
  }
}
