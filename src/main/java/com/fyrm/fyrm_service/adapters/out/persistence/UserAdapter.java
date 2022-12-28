package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.repository.UserRepository;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.domain.User;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class UserAdapter implements FindUserPort {

  private final UserRepository userRepository;

  @Override
  public List<User> findAllByName(String name) {
    return userRepository.findAllByNameContainsIgnoreCase(name)
        .stream()
        .map(userEntity -> User.builder().id(userEntity.getId()).name(userEntity.getName()).build())
        .toList();
  }
}
