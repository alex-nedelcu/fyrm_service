package com.fyrm.fyrm_service.adapters.in.web.converter.rentconnection;

import com.fyrm.fyrm_service.adapters.in.web.mapper.InitiatorStatusMapper;
import com.fyrm.fyrm_service.domain.InitiatorCurrentState;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.RentMateProposal;
import com.fyrm.fyrm_service.generatedapi.dtos.ActiveRentConnectionDto;
import com.fyrm.fyrm_service.generatedapi.dtos.InitiatorStatusDto;
import com.fyrm.fyrm_service.generatedapi.dtos.RentMateProposalDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitiatorCurrentStateConverter implements Converter<InitiatorCurrentState, InitiatorStatusDto> {

  private final InitiatorStatusMapper initiatorStatusMapper;
  private final ActiveRentConnectionConverter activeRentConnectionConverter;
  private final RentMateProposalConverter rentMateProposalConverter;

  @Override
  public InitiatorStatusDto apply(InitiatorCurrentState initiatorCurrentState) {
    if (initiatorCurrentState == null) {
      throw new IllegalArgumentException("Initiator current state must not be null for converting to dto");
    }

    var rentConnection = initiatorCurrentState.getRentConnection();
    var proposal = initiatorCurrentState.getRentMateProposal();

    return new InitiatorStatusDto()
        .initiatorStatus(initiatorStatusMapper.toGeneratedEnum(initiatorCurrentState.getStatus()))
        .hoursToWait(initiatorCurrentState.getHoursToWait())
        .activeRentConnection(convertToDtoOrElseNull(rentConnection))
        .associatedRentMateProposal(convertToDtoOrElseNull(proposal));
  }

  private ActiveRentConnectionDto convertToDtoOrElseNull(RentConnection rentConnection) {
    return rentConnection == null ? null : activeRentConnectionConverter.apply(rentConnection);
  }

  private RentMateProposalDto convertToDtoOrElseNull(RentMateProposal proposal) {
    return proposal == null ? null : rentMateProposalConverter.apply(proposal);
  }
}
