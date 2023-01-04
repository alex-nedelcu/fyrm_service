package com.fyrm.fyrm_service.application.service.confirmation_code;

import com.fyrm.fyrm_service.application.port.out.FindConfirmationCodePort;
import com.fyrm.fyrm_service.domain.ConfirmationCode;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

  private static final int CONFIRMATION_CODE_LENGTH = 6;
  private static final long EXPIRES_AFTER_MINUTES = 30L;
  private final FindConfirmationCodePort findConfirmationCodePort;

  public ConfirmationCode generateUniqueForUser(User user) {
    String code;

    do {
      code = RandomStringUtils.randomNumeric(CONFIRMATION_CODE_LENGTH);
    } while (findConfirmationCodePort.existsByCode(code));

    return ConfirmationCode.builder()
        .code(code)
        .user(user)
        .createdAt(ZonedDateTime.now())
        .expiresAt(ZonedDateTime.now().plusMinutes(EXPIRES_AFTER_MINUTES))
        .build();
  }
}
