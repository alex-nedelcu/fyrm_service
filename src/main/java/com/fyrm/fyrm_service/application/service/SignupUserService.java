package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.SignupUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.SignupUserUseCase;
import com.fyrm.fyrm_service.application.port.out.FindRolePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.PersistUserPort;
import com.fyrm.fyrm_service.application.service.confirmation_code.ConfirmationCodeService;
import com.fyrm.fyrm_service.application.service.email.EmailService;
import com.fyrm.fyrm_service.domain.ConfirmationCode;
import com.fyrm.fyrm_service.domain.exception.InvalidSignupInformationException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.ERole;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.Role;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@UseCase
@RequiredArgsConstructor
public class SignupUserService implements SignupUserUseCase {

  private final FindUserPort findUserPort;
  private final FindRolePort findRolePort;
  private final PersistUserPort persistUserPort;
  private final ConfirmationCodeService confirmationCodeService;
  private final PersistConfirmationCodePort persistConfirmationCodePort;
  private final EmailService emailService;
  private final PasswordEncoder encoder;

  @Override
  @Transactional
  public User signup(SignupUserCommand signupUserCommand) {
    validateSignupInformation(signupUserCommand);

    Role role = findRolePort.findByName(ERole.valueOf(signupUserCommand.getRole())).orElse(findRolePort.getDefaultRole());

    User user = User.builder()
        .username(signupUserCommand.getUsername())
        .email(signupUserCommand.getEmail())
        .password(encoder.encode(signupUserCommand.getPassword()))
        .roleId(role.getId())
        .firstName(signupUserCommand.getFirstName())
        .lastName(signupUserCommand.getLastName())
        .gender(signupUserCommand.getGender())
        .birthYear(signupUserCommand.getBirthYear())
        .enabled(false)
        .isSearching(true)
        .build();

    ConfirmationCode confirmationCode = confirmationCodeService.generateUniqueForUser(user);

    User savedUser = persistUserPort.persist(user);
    persistConfirmationCodePort.persist(confirmationCode);
    emailService.sendConfirmationEmail(user, confirmationCode);

    return savedUser;
  }

  private void validateSignupInformation(SignupUserCommand signupUserCommand) {
    if (findUserPort.existsByUsername(signupUserCommand.getUsername())) {
      throw new InvalidSignupInformationException("There is already an account with this username");
    }

    if (findUserPort.existsByEmail(signupUserCommand.getEmail())) {
      throw new InvalidSignupInformationException("There is already an account with this email");
    }

    // TODO: fetch these from properties file
    if (Stream.of("@ubb.ro", "@umf.ro").noneMatch(domain -> signupUserCommand.getEmail().contains(domain))) {
      throw new InvalidSignupInformationException("Please use your official student email address");
    }


    if (!EnumUtils.isValidEnum(ERole.class, signupUserCommand.getRole())) {
      throw new InvalidSignupInformationException("Role " + signupUserCommand.getRole() + " not supported!");
    }

    if (!findRolePort.existsByName(ERole.valueOf(signupUserCommand.getRole()))) {
      throw new InvalidSignupInformationException("Role " + signupUserCommand.getRole() + " not found!");
    }
  }
}
