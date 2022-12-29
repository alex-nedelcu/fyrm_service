package com.fyrm.fyrm_service.adapters.in.web.exception;

import static com.fyrm.fyrm_service.generatedapi.dtos.BusinessExceptionDto.CodeEnum.FIELD_NOT_VALID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import com.fyrm.fyrm_service.adapters.in.web.mapper.BusinessExceptionTypeMapper;
import com.fyrm.fyrm_service.domain.exception.base.BusinessException;
import com.fyrm.fyrm_service.generatedapi.dtos.BusinessExceptionDto;
import com.fyrm.fyrm_service.generatedapi.dtos.BusinessExceptionDto.CodeEnum;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageResourceService messageResourceService;
  private final BusinessExceptionTypeMapper businessExceptionTypeMapper;

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
    var businessExceptionDto = new BusinessExceptionDto().code(FIELD_NOT_VALID);
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

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> handleBusinessException(BusinessException exception, WebRequest webRequest) {
    String message;

    try {
      message = messageResourceService.getMessage(
          exception.getMessageKey(),
          Optional.ofNullable(exception.getParams()).stream().toArray(String[]::new)
      );
    } catch (NoSuchMessageException noSuchMessageException) {
      message = exception.getMessageKey();
    }

    CodeEnum code = businessExceptionTypeMapper.convert(exception.getType());

    var businessExceptionDto = new BusinessExceptionDto()
        .addMessagesItem(message)
        .code(code);

    return ResponseEntity
        .status(BAD_REQUEST)
        .headers(new HttpHeaders())
        .body(businessExceptionDto);
  }
}
