package com.fyrm.fyrm_service.adapters.out.stomp.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StompChatMessage {

  Long id;
  String content;
  Long fromId;
  String fromUsername;
  Long toId;
  String toUsername;
  String sentAtHoursMinutesFormat;
  String sentOnDayMonthYearFormat;
}
