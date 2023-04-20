package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class CreateSearchProfileCommand extends SelfValidating<CreateSearchProfileCommand> {

  @NotNull(message = "error.validation.user.id.is.mandatory")
  Long userId;

  @PositiveOrZero(message = "error.validation.search.profile.rent.price.lower.bound.must.be.positive")
  double rentPriceLowerBound;

  @Positive(message = "error.validation.search.profile.rent.price.upper.bound.must.be.positive")
  double rentPriceUpperBound;

  double latitude;

  double longitude;

  @PositiveOrZero(message = "error.validation.search.profile.maximum.age.gap.in.years.must.be.positive")
  int maximumAgeGapInYears;

  @NotNull(message = "error.validation.search.profile.genders.is.mandatory")
  @NotEmpty(message = "error.validation.search.profile.genders.is.empty")
  List<String> rentMatesGenderOptions;

  @NotNull(message = "error.validation.search.profile.rent.mates.count.is.mandatory")
  @NotEmpty(message = "error.validation.search.profile.rent.mates.count.is.empty")
  List<String> rentMateCountOptions;

  @NotNull(message = "error.validation.search.profile.hobbies.are.mandatory")
  @NotEmpty(message = "error.validation.search.profile.hobbies.are.empty")
  List<String> hobbyOptions;

  @NotNull(message = "error.validation.search.profile.bedroom.options.is.mandatory")
  @NotEmpty(message = "error.validation.search.profile.bedroom.options.is.empty")
  List<String> bedroomOptions;

  @NotNull(message = "error.validation.search.profile.bathroom.options.is.mandatory")
  @NotEmpty(message = "error.validation.search.profile.bathroom.options.is.empty")
  List<String> bathroomOptions;

  public CreateSearchProfileCommand(
      Long userId, double rentPriceLowerBound, double rentPriceUpperBound, double latitude, double longitude,
      int maximumAgeGapInYears, List<String> rentMatesGenderOptions, List<String> rentMateCountOptions,
      List<String> hobbyOptions, List<String> bedroomOptions, List<String> bathroomOptions
  ) {
    this.userId = userId;
    this.rentPriceLowerBound = rentPriceLowerBound;
    this.rentPriceUpperBound = rentPriceUpperBound;
    this.latitude = latitude;
    this.longitude = longitude;
    this.maximumAgeGapInYears = maximumAgeGapInYears;
    this.rentMatesGenderOptions = rentMatesGenderOptions;
    this.rentMateCountOptions = rentMateCountOptions;
    this.hobbyOptions = hobbyOptions;
    this.bedroomOptions = bedroomOptions;
    this.bathroomOptions = bathroomOptions;
    validateSelf();
  }
}
