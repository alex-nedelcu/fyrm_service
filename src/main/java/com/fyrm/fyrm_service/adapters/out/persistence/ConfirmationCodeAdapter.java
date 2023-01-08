package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.ConfirmationCodeEntity;
import com.fyrm.fyrm_service.adapters.out.persistence.mapper.ConfirmationCodeMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.ConfirmationCodeRepository;
import com.fyrm.fyrm_service.application.port.out.FindConfirmationCodePort;
import com.fyrm.fyrm_service.application.port.out.PersistConfirmationCodePort;
import com.fyrm.fyrm_service.domain.ConfirmationCode;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class ConfirmationCodeAdapter implements FindConfirmationCodePort, PersistConfirmationCodePort {

  private final ConfirmationCodeRepository confirmationCodeRepository;
  private final ConfirmationCodeMapper confirmationCodeMapper;

  @Override
  public Optional<ConfirmationCode> findByCode(String code) {
    Optional<ConfirmationCodeEntity> entity = confirmationCodeRepository.findByCode(code);
    return entity.map(confirmationCodeMapper::toDomain);
  }

  @Override
  public boolean existsByCode(String code) {
    return confirmationCodeRepository.existsByCode(code);
  }

  @Override
  public void persist(ConfirmationCode confirmationCode) {
    ConfirmationCodeEntity entity = confirmationCodeMapper.toEntity(confirmationCode);
    confirmationCodeRepository.save(entity);
  }
}
