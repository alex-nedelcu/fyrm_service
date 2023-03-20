package com.fyrm.fyrm_service.domain;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {

  Long id;
  String content;
  Long fromId;
  String fromUsername;
  Long toId;
  String toUsername;
  ZonedDateTime sentAt;
}
