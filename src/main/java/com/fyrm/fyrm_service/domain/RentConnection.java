package com.fyrm.fyrm_service.domain;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentConnection {

  Long id;
  Long initiatorId;
  int proposalMaximumSize;
  RentConnectionStatus status;
  List<SearchProfile> usedSearchProfiles;
  ZonedDateTime createdAt;

  public boolean isActive() {
    return status != null && status == RentConnectionStatus.ACTIVE;
  }
}
