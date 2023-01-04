package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.VerifiedStudent;

public interface FindVerifiedStudentPort {

  VerifiedStudent findByEmail(String email);
}
