package com.fyrm.fyrm_service.infrastructure.spring.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotBlank
  @Size(max = PASSWORD_MAXIMUM_SIZE)
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Column(name = "enabled", columnDefinition = "boolean default false", nullable = false)
  private Boolean enabled;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "birth_year", nullable = false)
  private Integer birthYear;

  @Column(name = "description")
  private String description;

  @Column(name = "gender")
  private String gender;

  @Column(name = "university")
  private String university;

  @Column(name = "is_searching", columnDefinition = "boolean default true", nullable = false)
  private Boolean isSearching;
}
