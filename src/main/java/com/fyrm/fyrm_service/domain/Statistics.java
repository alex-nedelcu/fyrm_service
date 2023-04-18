package com.fyrm.fyrm_service.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Statistics {

  List<Long> chattedWithUsers;
  List<Long> suggestedToUsers;
  List<Long> suggestedUsers;
}
