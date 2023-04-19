package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.RentMateProposalEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentMateProposalRepository extends BaseRepository<RentMateProposalEntity, Long> {

  Optional<RentMateProposalEntity> findByRentConnectionId(Long rentConnectionId);

  List<RentMateProposalEntity> findByRentConnectionIdIn(List<Long> rentConnectionIds);

  List<RentMateProposalEntity> findAllByProposedRentMates_UserIdIs(Long userId);
}
