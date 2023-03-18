package com.fyrm.fyrm_service.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProposedRentMate {

  Long id;
  Long userId;
  Long rentConnectionId;
  String username;
  String email;
  String description;
}
