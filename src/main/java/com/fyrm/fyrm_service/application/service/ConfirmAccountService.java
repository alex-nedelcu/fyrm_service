package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.ConfirmationCodeEntity;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.ConfirmationCodeRepository;
import com.fyrm.fyrm_service.application.port.in.command.ConfirmAccountCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.ConfirmAccountUseCase;
import com.fyrm.fyrm_service.domain.exception.InvalidConfirmationCodeException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import com.fyrm.fyrm_service.infrastructure.spring.security.repository.UserRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ConfirmAccountService implements ConfirmAccountUseCase {

  private final UserRepository userRepository;
  private final ConfirmationCodeRepository confirmationCodeRepository;

  @Override
  @Transactional
  public void confirm(ConfirmAccountCommand confirmAccountCommand) {
    String code = confirmAccountCommand.getCode();
    ConfirmationCodeEntity confirmationCodeEntity = confirmationCodeRepository
        .findByCode(code)
        .orElseThrow(() -> new InvalidConfirmationCodeException("Confirmation code is not associated to any account!"));

    User associatedUser = confirmationCodeEntity.getUser();

    confirmationCodeEntity.setConfirmedAt(ZonedDateTime.now());
    confirmationCodeRepository.save(confirmationCodeEntity);

    if (confirmedAfterExpiration(confirmationCodeEntity)) {
      throw new InvalidConfirmationCodeException("Confirmation code for user with username " + associatedUser.getUsername() + " has expired!");
    }

    associatedUser.setEnabled(true);
    userRepository.save(associatedUser);
  }

  private boolean confirmedAfterExpiration(ConfirmationCodeEntity confirmationCodeEntity) {
    return confirmationCodeEntity.getConfirmedAt().isAfter(confirmationCodeEntity.getExpiresAt());
  }
}
