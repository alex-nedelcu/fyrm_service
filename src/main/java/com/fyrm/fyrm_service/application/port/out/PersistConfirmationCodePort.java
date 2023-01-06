package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.ConfirmationCode;

public interface PersistConfirmationCodePort {

  void persist(ConfirmationCode confirmationCode);
}
