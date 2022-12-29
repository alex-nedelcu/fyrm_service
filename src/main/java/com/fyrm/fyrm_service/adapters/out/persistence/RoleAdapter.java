package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.application.port.out.FindRolePort;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.ERole;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.Role;
import com.fyrm.fyrm_service.infrastructure.spring.security.repository.RoleRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class RoleAdapter implements FindRolePort {

  private final RoleRepository roleRepository;

  @Override
  public Optional<Role> findByName(ERole name) {
    return roleRepository.findByName(name);
  }

  @Override
  public boolean existsByName(ERole name) {
    return roleRepository.existsByName(name);
  }
}
