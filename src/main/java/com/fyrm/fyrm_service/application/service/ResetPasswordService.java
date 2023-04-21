package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ResetPasswordCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ResetPasswordUseCase;
import com.fyrm.fyrm_service.application.port.out.FindConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.PersistUserPort;
import com.fyrm.fyrm_service.domain.exception.InvalidConfirmationCodeException;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.domain.exception.base.PasswordResetFailedException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Objects;

@UseCase
@RequiredArgsConstructor
public class ResetPasswordService implements ResetPasswordUseCase {

  private final PersistUserPort persistUserPort;
  private final FindUserPort findUserPort;
  private final PersistConfirmationCodePort persistConfirmationCodePort;
  private final FindConfirmationCodePort findConfirmationCodePort;
  private final PasswordEncoder encoder;

  @Override
  @Transactional
  public void reset(ResetPasswordCommand resetPasswordCommand) {
    var confirmationCode = findConfirmationCodePort
        .findByCode(resetPasswordCommand.getCode())
        .orElseThrow(() -> new InvalidConfirmationCodeException("Confirmation code is not associated to any account!"));
    var user = findUserPort
        .findByEmail(resetPasswordCommand.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("No user with email " + resetPasswordCommand.getEmail() + " found"));

    if (!Objects.equals(confirmationCode.getUser().getId(), user.getId())) {
      throw new PasswordResetFailedException("User trying to reset password is different than user associated to confirmation code!");
    }

    confirmationCode = confirmationCode.deepCloneWithConfirmedAt(ZonedDateTime.now());
    persistConfirmationCodePort.persist(confirmationCode);

    if (confirmationCode.wasConfirmedAfterExpiration()) {
      throw new InvalidConfirmationCodeException("Confirmation code for user with username " + user.getUsername() + " has expired!");
    }

    user.setPassword(encoder.encode(resetPasswordCommand.getPassword()));
    persistUserPort.persist(user);
  }
}
