package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.application.port.in.command.ConfirmAccountCommand;
import com.fyrm.fyrm_service.application.port.in.command.LoginUserCommand;
import com.fyrm.fyrm_service.application.port.in.command.SignupUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.ConfirmAccountUseCase;
import com.fyrm.fyrm_service.application.port.in.usecasse.LoginUserUseCase;
import com.fyrm.fyrm_service.application.port.in.usecasse.SignupUserUseCase;
import com.fyrm.fyrm_service.generatedapi.AuthenticationApi;
import com.fyrm.fyrm_service.generatedapi.dtos.JwtLoginResponseDto;
import com.fyrm.fyrm_service.generatedapi.dtos.LoginRequestDto;
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
  private final ConfirmAccountUseCase confirmAccountUseCase;
  private final LoginUserUseCase loginUserUseCase;

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

  @Override
  public ResponseEntity<MessageResponseDto> confirmAccount(String code) {
    ConfirmAccountCommand confirmAccountCommand = new ConfirmAccountCommand(code);
    confirmAccountUseCase.confirm(confirmAccountCommand);
    return ResponseEntity.ok(new MessageResponseDto().message("Account successfully confirmed!"));
  }

  @Override
  public ResponseEntity<JwtLoginResponseDto> loginUser(LoginRequestDto loginRequestDto) {
    LoginUserCommand loginUserCommand = new LoginUserCommand(
        loginRequestDto.getUsername(),
        loginRequestDto.getPassword()
    );
    JwtLoginResponseDto response = loginUserUseCase.login(loginUserCommand);
    return ResponseEntity.ok(response);
  }
}
