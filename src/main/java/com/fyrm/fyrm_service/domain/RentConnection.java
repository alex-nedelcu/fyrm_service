package com.fyrm.fyrm_service.domain;

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
}
