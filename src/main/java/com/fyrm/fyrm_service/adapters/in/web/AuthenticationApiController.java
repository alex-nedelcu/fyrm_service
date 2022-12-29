package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.application.port.in.command.SignupUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.SignupUserUseCase;
import com.fyrm.fyrm_service.generatedapi.AuthenticationApi;
import com.fyrm.fyrm_service.generatedapi.dtos.MessageResponseDto;
import com.fyrm.fyrm_service.generatedapi.dtos.SignupRequestDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class AuthenticationApiController implements AuthenticationApi {

  private final SignupUserUseCase signupUserUseCase;

  @Override
  public ResponseEntity<MessageResponseDto> signupUser(SignupRequestDto signupRequestDto) {
    var signupUserCommand = new SignupUserCommand(
        signupRequestDto.getUsername(),
        signupRequestDto.getEmail(),
        signupRequestDto.getPassword(),
        signupRequestDto.getRole()
    );
    signupUserUseCase.signup(signupUserCommand);
    return ResponseEntity.ok(new MessageResponseDto().message("User successfully signed up!"));
  }
}
