package com.fyrm.fyrm_service.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

  Long id;
  String name;
}
