package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.SignupUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.SignupUserUseCase;
import com.fyrm.fyrm_service.application.port.out.FindRolePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistUserPort;
import com.fyrm.fyrm_service.domain.exception.InvalidSignupInformationException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.ERole;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.Role;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class SignupUserService implements SignupUserUseCase {

  private final FindUserPort findUserPort;
  private final FindRolePort findRolePort;
  private final PersistUserPort persistUserPort;

  private final PasswordEncoder encoder;

  @Override
  public void signup(SignupUserCommand signupUserCommand) {
    validateUserData(signupUserCommand);

    String username = signupUserCommand.getUsername();
    String email = signupUserCommand.getEmail();
    String password = signupUserCommand.getPassword();
    String roleName = signupUserCommand.getRole();

    Role role = findRolePort.findByName(ERole.valueOf(roleName)).orElse(new Role(ERole.ROLE_USER));

    User user = User.builder()
        .username(username)
        .email(email)
        .password(encoder.encode(password))
        .role(role)
        .build();

    persistUserPort.persist(user);
  }

  private void validateUserData(SignupUserCommand signupUserCommand) {
    if (findUserPort.existsByUsername(signupUserCommand.getUsername())) {
      throw new InvalidSignupInformationException("Duplicate username!");
    }

    if (findUserPort.existsByEmail(signupUserCommand.getEmail())) {
      throw new InvalidSignupInformationException("Duplicate email!");
    }

    if (!EnumUtils.isValidEnum(ERole.class, signupUserCommand.getRole())) {
      throw new InvalidSignupInformationException("Role " + signupUserCommand.getRole() + " not supported!");
    }

    if (!findRolePort.existsByName(ERole.valueOf(signupUserCommand.getRole()))) {
      throw new InvalidSignupInformationException("Role " + signupUserCommand.getRole() + " not found!");
    }
  }
}
