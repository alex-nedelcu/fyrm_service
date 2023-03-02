package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ConfirmAccountCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.ConfirmAccountUseCase;
import com.fyrm.fyrm_service.application.port.out.FindConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.PersistConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.PersistUserPort;
import com.fyrm.fyrm_service.domain.ConfirmationCode;
import com.fyrm.fyrm_service.domain.exception.InvalidConfirmationCodeException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ConfirmAccountService implements ConfirmAccountUseCase {

  private final PersistUserPort persistUserPort;
  private final PersistConfirmationCodePort persistConfirmationCodePort;
  private final FindConfirmationCodePort findConfirmationCodePort;

  @Override
  @Transactional
  public void confirm(ConfirmAccountCommand confirmAccountCommand) {
    String code = confirmAccountCommand.getCode();
    ConfirmationCode confirmationCode = findConfirmationCodePort
        .findByCode(code)
        .orElseThrow(() -> new InvalidConfirmationCodeException("Confirmation code is not associated to any account!"));

    User associatedUser = confirmationCode.getUser();

    if (!Objects.equals(associatedUser.getId(), confirmAccountCommand.getUserId())) {
      throw new InvalidConfirmationCodeException("User trying to confirm account is different than user associated to confirmation code!");
    }

    confirmationCode = confirmationCode.deepCloneWithConfirmedAt(ZonedDateTime.now());
    persistConfirmationCodePort.persist(confirmationCode);

    if (confirmedAfterExpiration(confirmationCode)) {
      throw new InvalidConfirmationCodeException("Confirmation code for user with username " + associatedUser.getUsername() + " has expired!");
    }

    associatedUser.setEnabled(true);
    persistUserPort.persist(associatedUser);
  }

  private boolean confirmedAfterExpiration(ConfirmationCode confirmationCode) {
    return confirmationCode.getConfirmedAt().isAfter(confirmationCode.getExpiresAt());
  }
}
