package com.fyrm.fyrm_service.adapters.in.web.converter.rentconnection;

import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.generatedapi.dtos.ActiveRentConnectionDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActiveRentConnectionConverter implements Converter<RentConnection, ActiveRentConnectionDto> {

  @Override
  public ActiveRentConnectionDto apply(RentConnection rentConnection) {
    if (rentConnection == null) {
      throw new IllegalArgumentException("Rent connection must not be null for converting to dto");
    }

    return new ActiveRentConnectionDto()
        .id(rentConnection.getId())
        .initiatorId(rentConnection.getInitiatorId())
        .searchProfileIds(rentConnection.getUsedSearchProfiles().stream().map(SearchProfile::getId).toList());
  }
}
