package com.fyrm.fyrm_service.adapters.out.stomp.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StompNotification {

  Long id;
  String preview;
  Long fromId;
  String fromUsername;
  Long toId;
  String toUsername;
  boolean isRead;
  String sentOnDayMonthYearFormat;
}
