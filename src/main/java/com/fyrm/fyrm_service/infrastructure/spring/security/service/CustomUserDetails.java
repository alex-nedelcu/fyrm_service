package com.fyrm.fyrm_service.infrastructure.spring.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.io.Serial;
import java.util.Collection;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomUserDetails implements UserDetails {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String username;
  private String email;
  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public static CustomUserDetails build(User user) {
    var roleName = user.getRole().getName().name();
    var authorities = Stream.of(roleName).map(SimpleGrantedAuthority::new).toList();

    return new CustomUserDetails(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        authorities
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
