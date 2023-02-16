package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.application.port.in.command.CreateSearchProfileCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.CreateSearchProfileUseCase;
import com.fyrm.fyrm_service.generatedapi.SearchProfileApi;
import com.fyrm.fyrm_service.generatedapi.dtos.SearchProfileDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class SearchProfileApiController implements SearchProfileApi {

  private final CreateSearchProfileUseCase createSearchProfileUseCase;

  @Override
  public ResponseEntity<Void> createSearchProfile(SearchProfileDto searchProfileDto) {
    CreateSearchProfileCommand createSearchProfileCommand = new CreateSearchProfileCommand(
        searchProfileDto.getUserId(),
        searchProfileDto.getRentPriceLowerBound(),
        searchProfileDto.getRentPriceUpperBound(),
        searchProfileDto.getLatitude(),
        searchProfileDto.getLongitude(),
        searchProfileDto.getRentMatesGenderOptions(),
        searchProfileDto.getRentMateCountOptions(),
        searchProfileDto.getBedroomOptions(),
        searchProfileDto.getBathroomOptions()
    );
    createSearchProfileUseCase.create(createSearchProfileCommand);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
