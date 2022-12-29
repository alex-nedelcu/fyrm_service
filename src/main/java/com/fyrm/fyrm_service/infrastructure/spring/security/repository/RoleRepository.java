package com.fyrm.fyrm_service.infrastructure.spring.security.repository;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.ERole;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(ERole name);
}
