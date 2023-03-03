package com.fyrm.fyrm_service.domain;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentMateProposal {

  Long id;
  Long rentConnectionId;
  List<User> proposedRentMates;
}
