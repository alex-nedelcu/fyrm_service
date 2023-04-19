package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.adapters.in.web.mapper.StatisticsMapper;
import com.fyrm.fyrm_service.application.port.in.command.GetStatisticsByUserCommand;
import com.fyrm.fyrm_service.application.port.in.command.UpdateUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.GetStatisticsUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.UpdateUserUseCase;
import com.fyrm.fyrm_service.generatedapi.UserApi;
import com.fyrm.fyrm_service.generatedapi.dtos.StatisticsDto;
import com.fyrm.fyrm_service.generatedapi.dtos.UpdateUserDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class UserApiController implements UserApi {

  private final UpdateUserUseCase updateUserUseCase;
  private final GetStatisticsUseCase getStatisticsUseCase;
  private final StatisticsMapper statisticsMapper;

  @Override
  public ResponseEntity<StatisticsDto> getStatistics(Long userId) {
    var command = new GetStatisticsByUserCommand(userId);
    var statistics = getStatisticsUseCase.getByUser(command);
    return ResponseEntity.ok(statisticsMapper.map(statistics));
  }

  @Override
  public ResponseEntity<Void> updateUser(Long userId, UpdateUserDto updateUserDto) {
    UpdateUserCommand command = new UpdateUserCommand(
        userId,
        updateUserDto.getDescription(),
        updateUserDto.getIsSearching()
    );
    updateUserUseCase.update(command);
    return ResponseEntity.ok().build();
  }
}
