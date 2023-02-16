package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.application.port.in.command.UpdateUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.UpdateUserUseCase;
import com.fyrm.fyrm_service.generatedapi.UserApi;
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

  @Override
  public ResponseEntity<Void> updateUser(Long userId, UpdateUserDto updateUserDto) {
    UpdateUserCommand updateUserCommand = new UpdateUserCommand(
        userId,
        updateUserDto.getDescription(),
        updateUserDto.getIsSearching()
    );
    updateUserUseCase.update(updateUserCommand);
    return ResponseEntity.ok().build();
  }
}
