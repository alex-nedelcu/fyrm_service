package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.ConfirmationCode;
import java.util.Optional;

public interface FindConfirmationCodePort {

  Optional<ConfirmationCode> findByCode(String code);

  boolean existsByCode(String code);
}
