package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.ConfirmationCodeEntity;
import com.fyrm.fyrm_service.domain.ConfirmationCode;

@DomainEntityMapper
public class ConfirmationCodeMapper implements IDomainEntityMapper<ConfirmationCodeEntity, ConfirmationCode> {

  @Override
  public ConfirmationCodeEntity toEntity(ConfirmationCode confirmationCode) {
    return ConfirmationCodeEntity.builder()
        .id(confirmationCode.getId())
        .code(confirmationCode.getCode())
        .user(confirmationCode.getUser())
        .createdAt(confirmationCode.getCreatedAt())
        .expiresAt(confirmationCode.getExpiresAt())
        .confirmedAt(confirmationCode.getConfirmedAt())
        .build();
  }

  @Override
  public ConfirmationCode toDomain(ConfirmationCodeEntity confirmationCodeEntity) {
    return ConfirmationCode.builder()
        .id(confirmationCodeEntity.getId())
        .code(confirmationCodeEntity.getCode())
        .user(confirmationCodeEntity.getUser())
        .createdAt(confirmationCodeEntity.getCreatedAt())
        .expiresAt(confirmationCodeEntity.getExpiresAt())
        .confirmedAt(confirmationCodeEntity.getConfirmedAt())
        .build();
  }
}
