package com.fyrm.fyrm_service.application.port.in.usecasse;

import com.fyrm.fyrm_service.application.port.in.command.SignupUserCommand;

public interface SignupUserUseCase {

  void signup(SignupUserCommand signupUserCommand);
}
