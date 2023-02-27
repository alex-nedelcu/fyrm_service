package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.application.port.in.command.ConfirmAccountCommand;
import com.fyrm.fyrm_service.application.port.in.command.LoginUserCommand;
import com.fyrm.fyrm_service.application.port.in.command.ResendConfirmationCodeCommand;
import com.fyrm.fyrm_service.application.port.in.command.SignupUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ConfirmAccountUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.LoginUserUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.ResendConfirmationCodeUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.SignupUserUseCase;
import com.fyrm.fyrm_service.generatedapi.AuthenticationApi;
import com.fyrm.fyrm_service.generatedapi.dtos.ConfirmAccountRequestDto;
import com.fyrm.fyrm_service.generatedapi.dtos.JwtLoginResponseDto;
import com.fyrm.fyrm_service.generatedapi.dtos.LoginRequestDto;
import com.fyrm.fyrm_service.generatedapi.dtos.MessageResponseDto;
import com.fyrm.fyrm_service.generatedapi.dtos.ResendConfirmationCodeRequestDto;
import com.fyrm.fyrm_service.generatedapi.dtos.SignupRequestDto;
import com.fyrm.fyrm_service.generatedapi.dtos.SignupResponseDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
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
  private final ResendConfirmationCodeUseCase resendConfirmationCodeUseCase;

  @Override
  public ResponseEntity<SignupResponseDto> signupUser(SignupRequestDto signupRequestDto) {
    SignupUserCommand command = new SignupUserCommand(
        signupRequestDto.getUsername(),
        signupRequestDto.getEmail(),
        signupRequestDto.getPassword(),
        signupRequestDto.getRole()
    );

    User user = signupUserUseCase.signup(command);
    return ResponseEntity.ok(
        new SignupResponseDto()
            .userId(user.getId())
            .email(user.getEmail())
            .message("User successfully signed up!")
    );
  }

  @Override
  public ResponseEntity<MessageResponseDto> confirmAccount(String code, ConfirmAccountRequestDto confirmAccountRequestDto) {
    ConfirmAccountCommand command = new ConfirmAccountCommand(confirmAccountRequestDto.getUserId(), code);
    confirmAccountUseCase.confirm(command);
    return ResponseEntity.ok(new MessageResponseDto().message("Account successfully confirmed!"));
  }

  @Override
  public ResponseEntity<JwtLoginResponseDto> loginUser(LoginRequestDto loginRequestDto) {
    LoginUserCommand command = new LoginUserCommand(
        loginRequestDto.getUsername(),
        loginRequestDto.getPassword()
    );
    JwtLoginResponseDto response = loginUserUseCase.login(command);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> resendConfirmationCode(ResendConfirmationCodeRequestDto resendConfirmationCodeRequestDto) {
    ResendConfirmationCodeCommand command = new ResendConfirmationCodeCommand(resendConfirmationCodeRequestDto.getUserId());
    resendConfirmationCodeUseCase.resend(command);
    return ResponseEntity.noContent().build();
  }
}
