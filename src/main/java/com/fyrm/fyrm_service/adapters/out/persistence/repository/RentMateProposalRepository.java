package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.RentMateProposalEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RentMateProposalRepository extends BaseRepository<RentMateProposalEntity, Long> {

  Optional<RentMateProposalEntity> findByRentConnectionId(Long rentConnectionId);

  List<RentMateProposalEntity> findByRentConnectionIdIn(List<Long> rentConnectionIds);
}
