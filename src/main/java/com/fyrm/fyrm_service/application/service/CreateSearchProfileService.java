package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.CreateSearchProfileCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.CreateSearchProfileUseCase;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistSearchProfilePort;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateSearchProfileService implements CreateSearchProfileUseCase {

  private final FindUserPort findUserPort;
  private final PersistSearchProfilePort persistSearchProfilePort;

  @Override
  public void create(CreateSearchProfileCommand createSearchProfileCommand) {
    User user = findUserPort.findById(createSearchProfileCommand.getUserId()).orElseThrow(ResourceNotFoundException::new);
    SearchProfile searchProfile = SearchProfile
        .builder()
        .user(user)
        .rentPriceLowerBound(createSearchProfileCommand.getRentPriceLowerBound())
        .rentPriceUpperBound(createSearchProfileCommand.getRentPriceUpperBound())
        .latitude(createSearchProfileCommand.getLatitude())
        .longitude(createSearchProfileCommand.getLongitude())
        .maximumAgeGapInYears(createSearchProfileCommand.getMaximumAgeGapInYears())
        .rentMatesGenderOptions(createSearchProfileCommand.getRentMatesGenderOptions())
        .rentMateCountOptions(createSearchProfileCommand.getRentMateCountOptions())
        .hobbyOptions(createSearchProfileCommand.getHobbyOptions())
        .bedroomOptions(createSearchProfileCommand.getBedroomOptions())
        .bathroomOptions(createSearchProfileCommand.getBathroomOptions())
        .build();
    persistSearchProfilePort.persist(searchProfile);
  }
}
