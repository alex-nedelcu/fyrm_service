package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.ResendConfirmationCodeCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.ResendConfirmationCodeUseCase;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistConfirmationCodePort;
import com.fyrm.fyrm_service.application.service.confirmation_code.ConfirmationCodeService;
import com.fyrm.fyrm_service.application.service.email.EmailService;
import com.fyrm.fyrm_service.domain.ConfirmationCode;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ResendConfirmationCodeService implements ResendConfirmationCodeUseCase {

  private final FindUserPort findUserPort;
  private final ConfirmationCodeService confirmationCodeService;
  private final PersistConfirmationCodePort persistConfirmationCodePort;
  private final EmailService emailService;

  @Override
  public void resend(ResendConfirmationCodeCommand resendConfirmationCodeCommand) {
    User user = findUserPort.findById(resendConfirmationCodeCommand.getUserId()).orElseThrow(ResourceNotFoundException::new);
    ConfirmationCode confirmationCode = confirmationCodeService.generateUniqueForUser(user);
    persistConfirmationCodePort.persist(confirmationCode);
    emailService.sendConfirmationEmail(user, confirmationCode);
  }
}
