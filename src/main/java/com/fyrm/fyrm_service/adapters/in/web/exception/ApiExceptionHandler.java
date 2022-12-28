package com.fyrm.fyrm_service.adapters.in.web.exception;

import static com.fyrm.fyrm_service.generatedapi.dtos.BusinessExceptionDto.CodeEnum.INVALID_FIELD;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import com.fyrm.fyrm_service.generatedapi.dtos.BusinessExceptionDto;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageResourceService messageResourceService;

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
    var businessExceptionDto = new BusinessExceptionDto().code(INVALID_FIELD);
    var constraintViolations = CollectionUtils.emptyIfNull(exception.getConstraintViolations());

    constraintViolations
        .stream()
        .map(constraintViolation -> messageResourceService.getMessage(constraintViolation.getMessage()))
        .forEach(businessExceptionDto::addMessagesItem);

    return ResponseEntity
        .status(UNPROCESSABLE_ENTITY)
        .headers(new HttpHeaders())
        .body(businessExceptionDto);
  }
}
