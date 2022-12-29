package com.fyrm.fyrm_service.application.port.out;

public interface FindUserPort {

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
