package com.fyrm.fyrm_service.adapters.in.web.exception;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageResourceService {

  private final MessageSource messageSource;

  public String getMessage(String key, String... params) {
    return messageSource.getMessage(key, params, Locale.ENGLISH);
  }
}
