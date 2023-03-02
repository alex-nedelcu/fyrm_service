package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class UpdateSearchProfileCommand extends SelfValidating<UpdateSearchProfileCommand> {

  @NotNull(message = "error.validation.search.profile.id.is.mandatory")
  Long id;

  @PositiveOrZero(message = "error.validation.search.profile.rent.price.lower.bound.must.be.positive")
  double rentPriceLowerBound;

  @Positive(message = "error.validation.search.profile.rent.price.upper.bound.must.be.positive")
  double rentPriceUpperBound;

  double latitude;

  double longitude;

  @NotNull(message = "error.validation.search.profile.genders.is.mandatory")
  @NotEmpty(message = "error.validation.search.profile.genders.is.empty")
  List<String> rentMatesGenderOptions;

  @NotNull(message = "error.validation.search.profile.rent.mates.count.is.mandatory")
  @NotEmpty(message = "error.validation.search.profile.rent.mates.count.is.empty")
  List<String> rentMateCountOptions;

  @NotNull(message = "error.validation.search.profile.bedroom.options.is.mandatory")
  @NotEmpty(message = "error.validation.search.profile.bedroom.options.is.empty")
  List<String> bedroomOptions;

  @NotNull(message = "error.validation.search.profile.bathroom.options.is.mandatory")
  @NotEmpty(message = "error.validation.search.profile.bathroom.options.is.empty")
  List<String> bathroomOptions;

  public UpdateSearchProfileCommand(
      Long id, double rentPriceLowerBound, double rentPriceUpperBound, double latitude, double longitude,
      List<String> rentMatesGenderOptions, List<String> rentMateCountOptions,
      List<String> bedroomOptions, List<String> bathroomOptions
  ) {
    this.id = id;
    this.rentPriceLowerBound = rentPriceLowerBound;
    this.rentPriceUpperBound = rentPriceUpperBound;
    this.latitude = latitude;
    this.longitude = longitude;
    this.rentMatesGenderOptions = rentMatesGenderOptions;
    this.rentMateCountOptions = rentMateCountOptions;
    this.bedroomOptions = bedroomOptions;
    this.bathroomOptions = bathroomOptions;
    validateSelf();
  }
}
