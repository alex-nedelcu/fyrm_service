package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.ERole;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.Role;

import java.util.Optional;

public interface FindRolePort {

  Optional<Role> findByName(ERole name);

  boolean existsByName(ERole name);

  Role getDefaultRole();
}
