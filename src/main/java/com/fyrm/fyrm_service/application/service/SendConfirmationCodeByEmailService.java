package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.SendConfirmationCodeByEmailCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.SendConfirmationCodeByEmailUseCase;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistConfirmationCodePort;
import com.fyrm.fyrm_service.application.service.confirmation_code.ConfirmationCodeService;
import com.fyrm.fyrm_service.application.service.email.EmailService;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SendConfirmationCodeByEmailService implements SendConfirmationCodeByEmailUseCase {

  private final FindUserPort findUserPort;
  private final ConfirmationCodeService confirmationCodeService;
  private final PersistConfirmationCodePort persistConfirmationCodePort;
  private final EmailService emailService;


  @Override
  public void sendByEmail(SendConfirmationCodeByEmailCommand sendConfirmationCodeByEmailCommand) {
    var user = findUserPort.findByEmail(sendConfirmationCodeByEmailCommand.getEmail()).orElseThrow(ResourceNotFoundException::new);
    var confirmationCode = confirmationCodeService.generateUniqueForUser(user);
    persistConfirmationCodePort.persist(confirmationCode);
    emailService.sendConfirmationEmail(user, confirmationCode);
  }
}
