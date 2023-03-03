package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.ProposedRentMateEntity;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;

@DomainEntityMapper
@RequiredArgsConstructor
public class ProposedRentMateMapper implements IDomainEntityMapper<ProposedRentMateEntity, User> {

  @Override
  public ProposedRentMateEntity toEntity(User user) {
    return ProposedRentMateEntity.builder()
        .userId(user.getId())
        .build();
  }

  @Override
  public User toDomain(ProposedRentMateEntity proposedRentMateEntity) {
    throw new UnsupportedOperationException("Must not map proposed rent mate entity to user");
  }
}
