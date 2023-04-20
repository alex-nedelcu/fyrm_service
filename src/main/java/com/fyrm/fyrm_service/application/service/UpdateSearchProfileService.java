package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.UpdateSearchProfileCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.UpdateSearchProfileUseCase;
import com.fyrm.fyrm_service.application.port.out.FindSearchProfilePort;
import com.fyrm.fyrm_service.application.port.out.PersistSearchProfilePort;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateSearchProfileService implements UpdateSearchProfileUseCase {

  private final FindSearchProfilePort findSearchProfilePort;
  private final PersistSearchProfilePort persistSearchProfilePort;

  @Override
  public void update(UpdateSearchProfileCommand updateSearchProfileCommand) {
    var id = updateSearchProfileCommand.getId();
    SearchProfile searchProfile = findSearchProfilePort.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Search profile with id " + id + " not found")
    );

    searchProfile.setLatitude(updateSearchProfileCommand.getLatitude());
    searchProfile.setLongitude(updateSearchProfileCommand.getLongitude());
    searchProfile.setMaximumAgeGapInYears(updateSearchProfileCommand.getMaximumAgeGapInYears());
    searchProfile.setRentPriceLowerBound(updateSearchProfileCommand.getRentPriceLowerBound());
    searchProfile.setRentPriceUpperBound(updateSearchProfileCommand.getRentPriceUpperBound());
    searchProfile.setRentMateCountOptions(updateSearchProfileCommand.getRentMateCountOptions());
    searchProfile.setHobbyOptions(updateSearchProfileCommand.getHobbyOptions());
    searchProfile.setRentMatesGenderOptions(updateSearchProfileCommand.getRentMatesGenderOptions());
    searchProfile.setBedroomOptions(updateSearchProfileCommand.getBedroomOptions());
    searchProfile.setBathroomOptions(updateSearchProfileCommand.getBathroomOptions());
    persistSearchProfilePort.persist(searchProfile);
  }
}
