package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.util.Optional;

public interface FindUserPort {

  Optional<User> findById(Long id);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
