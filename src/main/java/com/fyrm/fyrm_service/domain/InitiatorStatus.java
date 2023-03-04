package com.fyrm.fyrm_service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum InitiatorStatus implements IdentifiableEnum {

  MUST_WAIT(0L),
  CAN_FINALISE(1L),
  CAN_CREATE(2L);

  @Getter
  private final Long id;
}
