package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.VerifiedStudentEntity;
import com.fyrm.fyrm_service.adapters.out.persistence.mapper.VerifiedStudentMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.VerifiedStudentRepository;
import com.fyrm.fyrm_service.application.port.out.FindVerifiedStudentPort;
import com.fyrm.fyrm_service.domain.VerifiedStudent;
import com.fyrm.fyrm_service.domain.exception.InvalidSignupInformationException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class VerifiedStudentAdapter implements FindVerifiedStudentPort {

  private final VerifiedStudentRepository verifiedStudentRepository;
  private final VerifiedStudentMapper verifiedStudentMapper;

  @Override
  public VerifiedStudent findByEmail(String email) {
    VerifiedStudentEntity entity = verifiedStudentRepository.findByEmail(email).orElseThrow(
        () -> new InvalidSignupInformationException("Sign up email " + email + " does not belong to verified student!")
    );

    return verifiedStudentMapper.toDomain(entity);
  }
}
