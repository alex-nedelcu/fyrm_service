package com.fyrm.fyrm_service.domain;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Notification {

  Long id;
  String preview;
  Long fromId;
  String fromUsername;
  Long toId;
  String toUsername;
  boolean isRead;
  ZonedDateTime sentAt;
}
