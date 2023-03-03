package com.fyrm.fyrm_service.adapters.in.web.converter.rentconnection;

import com.fyrm.fyrm_service.domain.RentMateProposal;
import com.fyrm.fyrm_service.generatedapi.dtos.RentMateProposalDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentMateProposalConverter implements Converter<RentMateProposal, RentMateProposalDto> {

  private final ProposedRentMateConverter proposedRentMateConverter;

  @Override
  public RentMateProposalDto apply(RentMateProposal rentMateProposal) {
    if (rentMateProposal == null) {
      throw new IllegalArgumentException("Rent mate proposal must not be null for converting to dto");
    }

    return new RentMateProposalDto()
        .rentConnectionId(rentMateProposal.getRentConnectionId())
        .proposalSize(rentMateProposal.getProposedRentMates().size())
        .rentMates(rentMateProposal.getProposedRentMates().stream().map(proposedRentMateConverter).toList());
  }
}
