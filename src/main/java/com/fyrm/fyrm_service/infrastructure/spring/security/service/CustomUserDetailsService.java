package com.fyrm.fyrm_service.infrastructure.spring.security.service;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import com.fyrm.fyrm_service.infrastructure.spring.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("Username " + username + " not found!")
    );

    return CustomUserDetails.build(user);
  }
}
