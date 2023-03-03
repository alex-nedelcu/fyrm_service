package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.adapters.in.web.converter.rentconnection.RentMateProposalConverter;
import com.fyrm.fyrm_service.application.port.in.command.ProposeRentMatesCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ProposeRentMatesUseCase;
import com.fyrm.fyrm_service.domain.RentMateProposal;
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

  private final ProposeRentMatesUseCase proposeRentMatesUseCase;
  private final RentMateProposalConverter rentMateProposalConverter;

  @Override

  public ResponseEntity<InitiatorStatusDto> findInitiatorStatus(Long userId) {
    return null;
  }

  @Override
  public ResponseEntity<RentMateProposalDto> proposeRentMates(RentConnectionDto rentConnectionDto) {
    ProposeRentMatesCommand command = new ProposeRentMatesCommand(
        rentConnectionDto.getInitiatorId(),
        rentConnectionDto.getProposalMaximumSize(),
        rentConnectionDto.getSearchProfileIds()
    );
    RentMateProposal proposal = proposeRentMatesUseCase.propose(command);
    return ResponseEntity.ok(rentMateProposalConverter.apply(proposal));
  }
}
