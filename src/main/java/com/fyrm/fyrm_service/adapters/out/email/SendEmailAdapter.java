package com.fyrm.fyrm_service.adapters.out.email;

import com.fyrm.fyrm_service.application.port.out.SendEmailPort;
import com.fyrm.fyrm_service.domain.email.EmailDetails;
import com.fyrm.fyrm_service.domain.exception.EmailSendingFailedException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

@OutboundAdapter
@RequiredArgsConstructor
public class SendEmailAdapter implements SendEmailPort {

  private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailAdapter.class);

  private final JavaMailSender mailSender;

  @Override
  @Async
  public void send(EmailDetails emailDetails) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, emailDetails.getEncoding());

      helper.setText(emailDetails.getContent(), emailDetails.isHtmlContent());
      helper.setTo(emailDetails.getTo());
      helper.setSubject(emailDetails.getSubject());
      helper.setFrom(emailDetails.getFrom());

      mailSender.send(message);
    } catch (MessagingException messagingException) {
      LOGGER.error("Error while sending confirmation email: {}", messagingException.toString());
      throw new EmailSendingFailedException("Error while sending confirmation email: " + messagingException);
    }
  }
}
