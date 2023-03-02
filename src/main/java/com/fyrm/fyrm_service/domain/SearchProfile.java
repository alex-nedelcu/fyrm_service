package com.fyrm.fyrm_service.domain;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchProfile {

  Long id;
  User user;
  double rentPriceLowerBound;
  double rentPriceUpperBound;
  double latitude;
  double longitude;
  List<String> rentMatesGenderOptions;
  List<String> rentMateCountOptions;
  List<String> bedroomOptions;
  List<String> bathroomOptions;
}
