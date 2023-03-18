package com.fyrm.fyrm_service.adapters.in.web.converter.rentconnection;

import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.generatedapi.dtos.RentConnectionDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentConnectionConverter implements Converter<RentConnection, RentConnectionDto> {

  @Override
  public RentConnectionDto apply(RentConnection rentConnection) {
    if (rentConnection == null) {
      throw new IllegalArgumentException("Rent connection must not be null for converting to dto");
    }

    return new RentConnectionDto()
        .initiatorId(rentConnection.getInitiatorId())
        .proposalMaximumSize(rentConnection.getProposalMaximumSize())
        .searchProfileIds(rentConnection.getUsedSearchProfiles().stream().map(SearchProfile::getId).toList());
  }
}
