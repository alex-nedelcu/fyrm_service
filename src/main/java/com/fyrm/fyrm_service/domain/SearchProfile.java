package com.fyrm.fyrm_service.domain;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchProfile {

  Long id;
  User user;
  double rentPriceLowerBound;
  double rentPriceUpperBound;
  double latitude;
  double longitude;
  int maximumAgeGapInYears;
  List<String> rentMatesGenderOptions;
  List<String> rentMateCountOptions;
  List<String> hobbyOptions;
  List<String> bedroomOptions;
  List<String> bathroomOptions;
}
