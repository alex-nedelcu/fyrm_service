package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.util.List;
import java.util.Optional;

public interface FindUserPort {

  Optional<User> findById(Long id);

  List<User> findAll();

  List<User> findSearchingUsersExcept(Long exceptId);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
