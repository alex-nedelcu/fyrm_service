package com.fyrm.fyrm_service.infrastructure.spring.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.ERole;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomUserDetails implements UserDetails {

  @Serial
  private static final long serialVersionUID = 3L;
  private String username;
  @JsonIgnore
  private String password;

  @Getter
  private Long id;
  @Getter
  private String email;
  @Getter
  private String firstName;
  @Getter
  private String lastName;
  @Getter
  private String gender;
  @Getter
  private String university;
  @Getter
  private Integer birthYear;
  @Getter
  private Boolean enabled;
  @Getter
  private String description;
  @Getter
  private Boolean isSearching;
  private Collection<? extends GrantedAuthority> authorities;

  public static CustomUserDetails build(User user) {
    var roleName = Arrays.stream(ERole.values())
        .filter(role -> role.getId().equals(user.getRoleId()))
        .map(Enum::name)
        .findFirst()
        .orElseThrow(ResourceNotFoundException::new);
    var authorities = Stream.of(roleName).map(SimpleGrantedAuthority::new).toList();

    return new CustomUserDetails(
        user.getUsername(),
        user.getPassword(),
        user.getId(),
        user.getEmail(),
        user.getFirstName(),
        user.getLastName(),
        user.getGender(),
        user.getUniversity(),
        user.getBirthYear(),
        user.getEnabled(),
        user.getDescription(),
        user.getIsSearching(),
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
