package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.SignupUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.SignupUserUseCase;
import com.fyrm.fyrm_service.application.port.out.FindRolePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.FindVerifiedStudentPort;
import com.fyrm.fyrm_service.application.port.out.PersistConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.PersistUserPort;
import com.fyrm.fyrm_service.application.service.confirmation_code.ConfirmationCodeService;
import com.fyrm.fyrm_service.application.service.email.EmailService;
import com.fyrm.fyrm_service.domain.ConfirmationCode;
import com.fyrm.fyrm_service.domain.VerifiedStudent;
import com.fyrm.fyrm_service.domain.exception.InvalidSignupInformationException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.ERole;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.Role;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SignupUserService implements SignupUserUseCase {

  private final FindUserPort findUserPort;
  private final FindRolePort findRolePort;
  private final PersistUserPort persistUserPort;
  private final FindVerifiedStudentPort findVerifiedStudentPort;
  private final ConfirmationCodeService confirmationCodeService;
  private final PersistConfirmationCodePort persistConfirmationCodePort;
  private final EmailService emailService;
  private final PasswordEncoder encoder;

  @Override
  @Transactional
  public User signup(SignupUserCommand signupUserCommand) {
    validateSignupInformation(signupUserCommand);

    String username = signupUserCommand.getUsername();
    String email = signupUserCommand.getEmail();
    String password = signupUserCommand.getPassword();
    String roleName = signupUserCommand.getRole();
    Role role = findRolePort.findByName(ERole.valueOf(roleName)).orElse(new Role(ERole.ROLE_USER));
    VerifiedStudent verifiedStudent = findVerifiedStudentPort.findByEmail(email);

    User user = User.builder()
        .username(username)
        .email(email)
        .password(encoder.encode(password))
        .role(role)
        .firstName(verifiedStudent.getFirstName())
        .lastName(verifiedStudent.getLastName())
        .birthDate(verifiedStudent.getBirthDate())
        .university(verifiedStudent.getUniversity())
        .faculty(verifiedStudent.getFaculty())
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

    if (!EnumUtils.isValidEnum(ERole.class, signupUserCommand.getRole())) {
      throw new InvalidSignupInformationException("Role " + signupUserCommand.getRole() + " not supported!");
    }

    if (!findRolePort.existsByName(ERole.valueOf(signupUserCommand.getRole()))) {
      throw new InvalidSignupInformationException("Role " + signupUserCommand.getRole() + " not found!");
    }
  }
}
