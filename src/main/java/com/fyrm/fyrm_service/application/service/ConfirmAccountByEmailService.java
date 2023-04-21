package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ConfirmAccountByEmailCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ConfirmAccountByEmailUseCase;
import com.fyrm.fyrm_service.application.port.out.FindConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.PersistUserPort;
import com.fyrm.fyrm_service.domain.ConfirmationCode;
import com.fyrm.fyrm_service.domain.exception.InvalidConfirmationCodeException;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.domain.exception.base.PasswordResetFailedException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Objects;

@UseCase
@RequiredArgsConstructor
public class ConfirmAccountByEmailService implements ConfirmAccountByEmailUseCase {

  private final PersistUserPort persistUserPort;
  private final PersistConfirmationCodePort persistConfirmationCodePort;
  private final FindConfirmationCodePort findConfirmationCodePort;
  private final FindUserPort findUserPort;

  @Override
  @Transactional
  public void confirm(ConfirmAccountByEmailCommand confirmAccountByEmailCommand) {
    var user = findUserPort
        .findByEmail(confirmAccountByEmailCommand.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("No user with email " + confirmAccountByEmailCommand.getEmail() + " found"));
    ConfirmationCode confirmationCode = findConfirmationCodePort
        .findByCode(confirmAccountByEmailCommand.getCode())
        .orElseThrow(() -> new InvalidConfirmationCodeException("Confirmation code is not associated to any account!"));

    if (!Objects.equals(confirmationCode.getUser().getId(), user.getId())) {
      throw new PasswordResetFailedException("User trying to confirm account is different than user associated to confirmation code!");
    }

    confirmationCode = confirmationCode.deepCloneWithConfirmedAt(ZonedDateTime.now());
    persistConfirmationCodePort.persist(confirmationCode);

    if (confirmationCode.wasConfirmedAfterExpiration()) {
      throw new InvalidConfirmationCodeException("Confirmation code for user with username " + user.getUsername() + " has expired!");
    }

    user.setEnabled(true);
    persistUserPort.persist(user);
  }
}
