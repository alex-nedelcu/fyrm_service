package com.fyrm.fyrm_service.infrastructure.spring.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

  private static final int USERNAME_MAXIMUM_SIZE = 32;
  private static final int EMAIL_MAXIMUM_SIZE = 64;
  private static final int PASSWORD_MAXIMUM_SIZE = 128;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true, updatable = false)
  private Long id;

  @NotBlank
  @Size(max = USERNAME_MAXIMUM_SIZE)
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @NotBlank
  @Size(max = EMAIL_MAXIMUM_SIZE)
  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotBlank
  @Size(max = PASSWORD_MAXIMUM_SIZE)
  @Column(name = "password", nullable = false)
  private String password;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;
}
