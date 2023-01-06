package com.fyrm.fyrm_service.domain;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ConfirmationCode {

  Long id;
  User user;
  String code;
  ZonedDateTime createdAt;
  ZonedDateTime expiresAt;
  ZonedDateTime confirmedAt;
}
