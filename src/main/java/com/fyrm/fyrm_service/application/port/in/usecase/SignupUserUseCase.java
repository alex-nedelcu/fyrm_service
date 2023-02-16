package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.SignupUserCommand;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;

public interface SignupUserUseCase {

  User signup(SignupUserCommand signupUserCommand);
}
