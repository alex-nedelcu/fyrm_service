package com.fyrm.fyrm_service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RentConnectionStatus implements IdentifiableEnum {

  ACTIVE(0L),
  SUCCESS(1L),
  FAILURE(2L);

  @Getter
  private final Long id;
}
