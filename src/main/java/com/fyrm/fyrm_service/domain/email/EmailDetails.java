package com.fyrm.fyrm_service.domain.email;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailDetails {

  String to;
  String from;
  String subject;
  String content;
  String encoding;
  boolean isHtmlContent;
}
