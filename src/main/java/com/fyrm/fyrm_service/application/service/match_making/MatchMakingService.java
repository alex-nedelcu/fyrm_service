package com.fyrm.fyrm_service.application.service.match_making;

import com.fyrm.fyrm_service.domain.SearchProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.util.SloppyMath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchMakingService {

  private static final String ANY = "any";
  private final List<String> COUNT_OPTIONS_LARGER_THAN_ONE = List.of("2", "3", ">3");
  private final List<Pair<Double, Double>> DISTANCE_CATEGORIES = List.of(Pair.of(1.0, 50.0), Pair.of(3.0, 35.0), Pair.of(5.0, 15.0), Pair.of(8.0, 5.0));
  private final List<Pair<Double, Double>> PRICE_DIFFERENCE_CATEGORIES = List.of(Pair.of(50.0, 50.0), Pair.of(100.0, 30.0), Pair.of(200.0, 10.0));

  @Value("${fyrm.match.making.is.match.two.options}")
  private double IS_MATCH_TWO_OPTIONS_SCORE;
  @Value("${fyrm.match.making.is.not.match.two.options}")
  private double IS_NOT_MATCH_TWO_OPTIONS_SCORE;
  @Value("${fyrm.match.making.is.not.match.multiple.options}")
  private double IS_NOT_MATCH_MULTIPLE_OPTIONS;
  @Value("${fyrm.match.making.weight.bathroom.options}")
  private double BATHROOM_OPTIONS_WEIGHT;
  @Value("${fyrm.match.making.weight.count.options}")
  private double COUNT_OPTIONS_WEIGHT;

  public double computeMatchingScore(SearchProfile first, SearchProfile second) {
    return computeRentPriceScore(first, second) +
        computeDesiredLocationMatch(first, second) +
        computeGenderOptionsMatch(first, second) +
        computeCountOptionsMatch(first, second) +
        computeBedroomOptionsMatch(first, second) +
        computeBathroomOptionsMatch(first, second);
  }

  private double computeBathroomOptionsMatch(SearchProfile first, SearchProfile second) {
    return BATHROOM_OPTIONS_WEIGHT * commonElementsCount(first.getBathroomOptions(), second.getBathroomOptions());
  }

  private double computeBedroomOptionsMatch(SearchProfile first, SearchProfile second) {
    String firstBedroomOption = first.getBedroomOptions().get(0);
    String secondBedroomOption = second.getBedroomOptions().get(0);

    boolean any = isAny(firstBedroomOption) || isAny(secondBedroomOption);
    boolean areIdentical = areIdentical(firstBedroomOption, secondBedroomOption);
    boolean haveCommonCountOptionLargerThanOne = commonCountOptionLargerThanOne(first.getRentMateCountOptions(), second.getRentMateCountOptions());
    boolean areDifferentButCompatible = !areIdentical && haveCommonCountOptionLargerThanOne;

    boolean match = any || areIdentical || areDifferentButCompatible;

    return match ? IS_MATCH_TWO_OPTIONS_SCORE : IS_NOT_MATCH_TWO_OPTIONS_SCORE;
  }

  private double computeCountOptionsMatch(SearchProfile first, SearchProfile second) {
    return COUNT_OPTIONS_WEIGHT * commonElementsCount(first.getRentMateCountOptions(), second.getRentMateCountOptions());
  }

  private double computeGenderOptionsMatch(SearchProfile first, SearchProfile second) {
    String firstGenderOption = first.getRentMatesGenderOptions().get(0);
    String secondGenderOption = second.getRentMatesGenderOptions().get(0);

    boolean match = isAny(firstGenderOption)
        || isAny(secondGenderOption)
        || areIdentical(firstGenderOption, secondGenderOption);

    return match ? IS_MATCH_TWO_OPTIONS_SCORE : IS_NOT_MATCH_TWO_OPTIONS_SCORE;
  }

  private double computeDesiredLocationMatch(SearchProfile first, SearchProfile second) {
    double kilometers = distanceInKilometersBetween(first.getLatitude(), first.getLongitude(), second.getLatitude(), second.getLongitude());

    for (Pair<Double, Double> pair : DISTANCE_CATEGORIES) {
      Double kilometersThreshold = pair.getFirst();
      Double scoreIfMatches = pair.getSecond();

      if (kilometers <= kilometersThreshold) {
        return scoreIfMatches;
      }
    }

    return IS_NOT_MATCH_MULTIPLE_OPTIONS;
  }

  private double computeRentPriceScore(SearchProfile first, SearchProfile second) {
    double firstAverage = (first.getRentPriceLowerBound() + first.getRentPriceUpperBound()) / 2.0;
    double secondAverage = (second.getRentPriceLowerBound() + second.getRentPriceUpperBound()) / 2.0;
    double difference = StrictMath.abs(firstAverage - secondAverage);

    for (Pair<Double, Double> pair : PRICE_DIFFERENCE_CATEGORIES) {
      Double priceDifferenceThreshold = pair.getFirst();
      Double scoreIfMatches = pair.getSecond();

      if (difference < priceDifferenceThreshold) {
        return scoreIfMatches;
      }
    }

    return IS_NOT_MATCH_MULTIPLE_OPTIONS;
  }

  private double distanceInKilometersBetween(double latitude1, double longitude1, double latitude2, double longitude2) {
    double kilometerInMeters = 1000.0;
    return SloppyMath.haversinMeters(latitude1, longitude1, latitude2, longitude2) / kilometerInMeters;
  }

  private boolean commonCountOptionLargerThanOne(List<String> firstCountOptions, List<String> secondCountOptions) {
    List<String> commonOptions = new ArrayList<>(firstCountOptions);
    commonOptions.retainAll(secondCountOptions);
    return !Collections.disjoint(commonOptions, COUNT_OPTIONS_LARGER_THAN_ONE);
  }

  private boolean isAny(String option) {
    return option.equalsIgnoreCase(ANY);
  }

  private boolean areIdentical(String first, String second) {
    return first.equalsIgnoreCase(second);
  }

  private double toDouble(int value) {
    return value;
  }

  private double commonElementsCount(Collection<String> first, Collection<String> second) {
    Collection<String> firstCopy = new ArrayList<>(first);
    firstCopy.retainAll(second);
    return toDouble(firstCopy.size());
  }
}
