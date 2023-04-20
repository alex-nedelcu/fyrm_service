package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.SearchProfileEntity;
import com.fyrm.fyrm_service.domain.SearchProfile;

@DomainEntityMapper
public class SearchProfileMapper implements IDomainEntityMapper<SearchProfileEntity, SearchProfile> {

  @Override
  public SearchProfileEntity toEntity(SearchProfile searchProfile) {
    return SearchProfileEntity.builder()
        .id(searchProfile.getId())
        .user(searchProfile.getUser())
        .rentPriceLowerBound(searchProfile.getRentPriceLowerBound())
        .rentPriceUpperBound(searchProfile.getRentPriceUpperBound())
        .latitude(searchProfile.getLatitude())
        .longitude(searchProfile.getLongitude())
        .maximumAgeGapInYears(searchProfile.getMaximumAgeGapInYears())
        .rentMatesGenderOptions(searchProfile.getRentMatesGenderOptions())
        .rentMateCountOptions(searchProfile.getRentMateCountOptions())
        .hobbyOptions(searchProfile.getHobbyOptions())
        .bedroomOptions(searchProfile.getBedroomOptions())
        .bathroomOptions(searchProfile.getBathroomOptions())
        .build();
  }

  @Override
  public SearchProfile toDomain(SearchProfileEntity searchProfileEntity) {
    return SearchProfile.builder()
        .id(searchProfileEntity.getId())
        .user(searchProfileEntity.getUser())
        .rentPriceLowerBound(searchProfileEntity.getRentPriceLowerBound())
        .rentPriceUpperBound(searchProfileEntity.getRentPriceUpperBound())
        .latitude(searchProfileEntity.getLatitude())
        .longitude(searchProfileEntity.getLongitude())
        .maximumAgeGapInYears(searchProfileEntity.getMaximumAgeGapInYears())
        .rentMatesGenderOptions(searchProfileEntity.getRentMatesGenderOptions())
        .rentMateCountOptions(searchProfileEntity.getRentMateCountOptions())
        .hobbyOptions(searchProfileEntity.getHobbyOptions())
        .bedroomOptions(searchProfileEntity.getBedroomOptions())
        .bathroomOptions(searchProfileEntity.getBathroomOptions())
        .build();
  }
}
