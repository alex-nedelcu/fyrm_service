package com.fyrm.fyrm_service.adapters.in.web.converter.searchprofile;

import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.generatedapi.dtos.SearchProfileByUserDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProfileByUserConverter implements Converter<SearchProfile, SearchProfileByUserDto> {

  @Override
  public SearchProfileByUserDto apply(SearchProfile searchProfile) {
    if (searchProfile == null) {
      throw new IllegalArgumentException("Search profile must not be null for converting to dto");
    }

    return new SearchProfileByUserDto()
        .id(searchProfile.getId())
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
