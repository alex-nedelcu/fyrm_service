package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.RentConnectionEntity;
import com.fyrm.fyrm_service.adapters.out.persistence.entity.base.Auditable;
import com.fyrm.fyrm_service.adapters.out.persistence.mapper.RentConnectionMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.RentConnectionRepository;
import com.fyrm.fyrm_service.application.port.out.FindRentConnectionPort;
import com.fyrm.fyrm_service.application.port.out.PersistRentConnectionPort;
import com.fyrm.fyrm_service.domain.RentConnection;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.fyrm.fyrm_service.domain.RentConnectionStatus.ACTIVE;

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
  public Long persist(RentConnection rentConnection) {
    var savedRentConnectionEntity = rentConnectionRepository.save(rentConnectionMapper.toEntity(rentConnection));
    return savedRentConnectionEntity.getId();
  }

  @Override
  public Optional<RentConnection> findById(Long id) {
    Optional<RentConnectionEntity> optionalEntity = rentConnectionRepository.findById(id);
    return optionalEntity.map(rentConnectionMapper::toDomain);
  }

  @Override
  public Optional<RentConnection> findLatestActiveByUserId(Long userId) {
    Optional<RentConnectionEntity> optionalEntity = rentConnectionRepository.findFirstByInitiatorIdAndStatusOrderByCreatedAtDesc(
        userId, ACTIVE
    );
    return optionalEntity.map(rentConnectionMapper::toDomain);
  }

  @Override
  public boolean hasAnyActive(Long userId) {
    List<RentConnection> rentConnections = rentConnectionMapper.toDomainList(rentConnectionRepository.findAllByInitiatorId(userId));
    return rentConnections.stream().anyMatch(RentConnection::isActive);
  }

  @Override
  public List<Long> findAllByUserIdNewerThanDays(Long userId, int days) {
    return rentConnectionRepository.findAllByInitiatorIdAndCreatedAtAfter(userId, ZonedDateTime.now().minusDays(days))
        .stream()
        .map(Auditable::getId)
        .toList();
  }

  @Override
  public int findActiveRentConnectionsCount() {
    return rentConnectionRepository.countByStatus(ACTIVE);
  }

  @Override
  public List<RentConnection> findAllInitiatedBy(Long userId) {
    return rentConnectionMapper.toDomainList(rentConnectionRepository.findAllByInitiatorId(userId));
  }
}
