package com.fyrm.fyrm_service.domain;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VerifiedStudent {

  Long id;
  String email;
  String firstName;
  String lastName;
  ZonedDateTime birthDate;
  String university;
  String faculty;
  String gender;
}
