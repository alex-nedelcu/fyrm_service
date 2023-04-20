package com.fyrm.fyrm_service.infrastructure.spring.security.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ERole {
  ROLE_USER(0L);

  @Getter
  private final Long id;
}
