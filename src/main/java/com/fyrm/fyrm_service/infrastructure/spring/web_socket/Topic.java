package com.fyrm.fyrm_service.infrastructure.spring.web_socket;

import lombok.Getter;

public enum Topic {
  PRIVATE_MESSAGES("/queue/private-messages");

  @Getter
  private final String name;

  Topic(String name) {
    this.name = name;
  }
}
