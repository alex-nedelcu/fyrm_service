package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistUserPort;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import com.fyrm.fyrm_service.infrastructure.spring.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@OutboundAdapter
@RequiredArgsConstructor
public class UserAdapter implements FindUserPort, PersistUserPort {

  private final UserRepository userRepository;

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public List<User> findSearchingUsersExcept(Long exceptId) {
    return userRepository.findByIsSearchingAndIdNot(true, exceptId);
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public User persist(User user) {
    return userRepository.save(user);
  }
}
