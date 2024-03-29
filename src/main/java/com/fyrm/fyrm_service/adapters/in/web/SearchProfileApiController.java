package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.adapters.in.web.converter.searchprofile.SearchProfilesByUserConverter;
import com.fyrm.fyrm_service.application.port.in.command.CreateSearchProfileCommand;
import com.fyrm.fyrm_service.application.port.in.command.DeleteSearchProfileCommand;
import com.fyrm.fyrm_service.application.port.in.command.FindSearchProfilesByUserCommand;
import com.fyrm.fyrm_service.application.port.in.command.UpdateSearchProfileCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.CreateSearchProfileUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.DeleteSearchProfileUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.FindSearchProfilesByUserUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.UpdateSearchProfileUseCase;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.generatedapi.SearchProfileApi;
import com.fyrm.fyrm_service.generatedapi.dtos.CreateSearchProfileDto;
import com.fyrm.fyrm_service.generatedapi.dtos.SearchProfilesByUserDto;
import com.fyrm.fyrm_service.generatedapi.dtos.UpdateSearchProfileDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class SearchProfileApiController implements SearchProfileApi {

  private final CreateSearchProfileUseCase createSearchProfileUseCase;
  private final FindSearchProfilesByUserUseCase findSearchProfilesByUserUseCase;
  private final DeleteSearchProfileUseCase deleteSearchProfileUseCase;
  private final UpdateSearchProfileUseCase updateSearchProfileUseCase;
  private final SearchProfilesByUserConverter searchProfilesByUserConverter;

  @Override
  public ResponseEntity<Void> create(CreateSearchProfileDto searchProfileDto) {
    CreateSearchProfileCommand command = new CreateSearchProfileCommand(
        searchProfileDto.getUserId(),
        searchProfileDto.getRentPriceLowerBound(),
        searchProfileDto.getRentPriceUpperBound(),
        searchProfileDto.getLatitude(),
        searchProfileDto.getLongitude(),
        searchProfileDto.getMaximumAgeGapInYears(),
        searchProfileDto.getRentMatesGenderOptions(),
        searchProfileDto.getRentMateCountOptions(),
        searchProfileDto.getHobbyOptions(),
        searchProfileDto.getBedroomOptions(),
        searchProfileDto.getBathroomOptions()
    );
    createSearchProfileUseCase.create(command);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  public ResponseEntity<Void> deleteSearchProfile(Long id) {
    DeleteSearchProfileCommand command = new DeleteSearchProfileCommand(id);
    deleteSearchProfileUseCase.delete(command);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<SearchProfilesByUserDto> findAll(Long userId) {
    FindSearchProfilesByUserCommand command = new FindSearchProfilesByUserCommand(userId);
    List<SearchProfile> searchProfilesByUser = findSearchProfilesByUserUseCase.find(command);
    return ResponseEntity.ok(searchProfilesByUserConverter.apply(searchProfilesByUser));
  }

  @Override
  public ResponseEntity<Void> updateSearchProfile(Long id, UpdateSearchProfileDto updateSearchProfileDto) {
    UpdateSearchProfileCommand command = new UpdateSearchProfileCommand(
        id,
        updateSearchProfileDto.getRentPriceLowerBound(),
        updateSearchProfileDto.getRentPriceUpperBound(),
        updateSearchProfileDto.getLatitude(),
        updateSearchProfileDto.getLongitude(),
        updateSearchProfileDto.getMaximumAgeGapInYears(),
        updateSearchProfileDto.getRentMatesGenderOptions(),
        updateSearchProfileDto.getRentMateCountOptions(),
        updateSearchProfileDto.getHobbyOptions(),
        updateSearchProfileDto.getBedroomOptions(),
        updateSearchProfileDto.getBathroomOptions()
    );
    updateSearchProfileUseCase.update(command);
    return ResponseEntity.noContent().build();
  }
}
