package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.RentConnectionEntity;
import com.fyrm.fyrm_service.adapters.out.persistence.mapper.RentConnectionMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.RentConnectionRepository;
import com.fyrm.fyrm_service.application.port.out.FindRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentConnectionPort;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class RentConnectionAdapter implements PersistRentConnectionPort, FindRentConnectionPort {

  private final RentConnectionRepository rentConnectionRepository;
  private final RentConnectionMapper rentConnectionMapper;

  @Override
  public Long persistAndFlush(RentConnection rentConnection) {
    var savedRentConnectionEntity = rentConnectionRepository.saveAndFlush(rentConnectionMapper.toEntity(rentConnection));
    return savedRentConnectionEntity.getId();
  }

  @Override
  public Optional<RentConnection> findById(Long id) {
    Optional<RentConnectionEntity> optionalEntity = rentConnectionRepository.findById(id);
    return optionalEntity.map(rentConnectionMapper::toDomain);
  }
}
