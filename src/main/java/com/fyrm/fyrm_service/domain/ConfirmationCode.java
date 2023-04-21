package com.fyrm.fyrm_service.domain;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class ConfirmationCode {

  Long id;
  User user;
  String code;
  ZonedDateTime createdAt;
  ZonedDateTime expiresAt;
  ZonedDateTime confirmedAt;

  public ConfirmationCode deepCloneWithConfirmedAt(ZonedDateTime newConfirmedAt) {
    return builder()
        .id(id)
        .user(user)
        .code(code)
        .createdAt(createdAt)
        .expiresAt(expiresAt)
        .confirmedAt(newConfirmedAt)
        .build();
  }

  public boolean wasConfirmedAfterExpiration() {
    return confirmedAt != null && expiresAt != null && confirmedAt.isAfter(expiresAt);
  }
}
