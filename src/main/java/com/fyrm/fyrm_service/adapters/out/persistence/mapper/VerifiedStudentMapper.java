package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.VerifiedStudentEntity;
import com.fyrm.fyrm_service.domain.VerifiedStudent;

@DomainEntityMapper
public class VerifiedStudentMapper implements IDomainEntityMapper<VerifiedStudentEntity, VerifiedStudent> {

  @Override
  public VerifiedStudentEntity toEntity(VerifiedStudent verifiedStudent) {
    return VerifiedStudentEntity.builder()
        .id(verifiedStudent.getId())
        .email(verifiedStudent.getEmail())
        .firstName(verifiedStudent.getFirstName())
        .lastName(verifiedStudent.getLastName())
        .birthDate(verifiedStudent.getBirthDate())
        .university(verifiedStudent.getUniversity())
        .faculty(verifiedStudent.getLastName())
        .build();
  }

  @Override
  public VerifiedStudent toDomain(VerifiedStudentEntity verifiedStudentEntity) {
    return VerifiedStudent.builder()
        .id(verifiedStudentEntity.getId())
        .email(verifiedStudentEntity.getEmail())
        .firstName(verifiedStudentEntity.getFirstName())
        .lastName(verifiedStudentEntity.getLastName())
        .birthDate(verifiedStudentEntity.getBirthDate())
        .university(verifiedStudentEntity.getUniversity())
        .faculty(verifiedStudentEntity.getFaculty())
        .build();
  }
}
