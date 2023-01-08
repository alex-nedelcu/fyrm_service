package com.fyrm.fyrm_service.domain.exception;

import static com.fyrm.fyrm_service.domain.exception.base.BusinessExceptionType.RESOURCE_NOT_FOUND;

import com.fyrm.fyrm_service.domain.exception.base.BusinessException;
import java.util.List;

public class ResourceNotFoundException extends BusinessException {

  public ResourceNotFoundException() {
    super(RESOURCE_NOT_FOUND, "error.exception.resource.not.found", null);
  }

  public ResourceNotFoundException(String message) {
    super(RESOURCE_NOT_FOUND, message, null);
  }

  public ResourceNotFoundException(List<String> params) {
    super(RESOURCE_NOT_FOUND, "error.exception.resource.not.found", params);
  }
}
