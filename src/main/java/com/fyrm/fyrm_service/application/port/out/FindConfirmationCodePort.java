package com.fyrm.fyrm_service.application.port.out;

public interface FindConfirmationCodePort {

  boolean existsByCode(String code);
}
