package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.application.port.out.FindRolePort;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.ERole;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.Role;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@OutboundAdapter
@RequiredArgsConstructor
public class MockRoleAdapter implements FindRolePort {
  @Override
  public Optional<Role> findByName(ERole name) {
    return Optional.of(getDefaultRole());
  }

  @Override
  public boolean existsByName(ERole name) {
    return true;
  }

  @Override
  public Role getDefaultRole() {
    return Role.builder().id(ERole.ROLE_USER.getId()).build();
  }
}
