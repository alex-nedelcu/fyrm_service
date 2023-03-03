package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.generatedapi.RentConnectionApi;
import com.fyrm.fyrm_service.generatedapi.dtos.InitiatorStatusDto;
import com.fyrm.fyrm_service.generatedapi.dtos.RentConnectionDto;
import com.fyrm.fyrm_service.generatedapi.dtos.RentMateProposalDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class RentConnectionApiController implements RentConnectionApi {

  @Override
  public ResponseEntity<InitiatorStatusDto> findInitiatorStatus(Long userId) {
    return null;
  }

  @Override
  public ResponseEntity<RentMateProposalDto> proposeRentMates(RentConnectionDto rentConnectionDto) {
    return null;
  }
}
