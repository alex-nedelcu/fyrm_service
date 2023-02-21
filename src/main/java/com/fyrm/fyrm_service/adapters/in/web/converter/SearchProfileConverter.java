package com.fyrm.fyrm_service.adapters.in.web.converter;

import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.generatedapi.dtos.SearchProfileDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProfileConverter implements Converter<SearchProfile, SearchProfileDto> {

  @Override
  public SearchProfileDto apply(SearchProfile searchProfile) {
    if (searchProfile == null) {
      throw new IllegalArgumentException("Search profile must not be null for converting to dto");
    }

    return new SearchProfileDto()
        .userId(searchProfile.getUser().getId())
        .rentPriceLowerBound(searchProfile.getRentPriceLowerBound())
        .rentPriceUpperBound(searchProfile.getRentPriceUpperBound())
        .latitude(searchProfile.getLatitude())
        .longitude(searchProfile.getLongitude())
        .bathroomOptions(searchProfile.getBathroomOptions())
        .bedroomOptions(searchProfile.getBedroomOptions())
        .rentMateCountOptions(searchProfile.getRentMateCountOptions())
        .rentMatesGenderOptions(searchProfile.getRentMatesGenderOptions());
  }
}
