package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.adapters.in.web.converter.UserDtoConverter;
import com.fyrm.fyrm_service.application.port.in.command.GetUsersByNameCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.GetUsersByNameUseCase;
import com.fyrm.fyrm_service.generatedapi.UserApi;
import com.fyrm.fyrm_service.generatedapi.dtos.UserDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class UserApiController implements UserApi {

  private final GetUsersByNameUseCase getUsersByNameUseCase;
  private final UserDtoConverter userDtoConverter;

  @Override
  public ResponseEntity<List<UserDto>> getUsersByName(String name) {
    var getUsersByNameCommand = new GetUsersByNameCommand(name);
    var users = getUsersByNameUseCase.get(getUsersByNameCommand);
    return ResponseEntity.ok(users.stream().map(userDtoConverter).toList());
  }
}
