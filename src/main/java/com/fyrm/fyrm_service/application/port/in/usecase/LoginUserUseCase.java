package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.LoginUserCommand;
import com.fyrm.fyrm_service.generatedapi.dtos.JwtLoginResponseDto;

public interface LoginUserUseCase {

  JwtLoginResponseDto login(LoginUserCommand loginUserCommand);
}
