package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.ConfirmationCodeEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationCodeRepository extends BaseRepository<ConfirmationCodeEntity, Long> {

  boolean existsByCode(String code);

  Optional<ConfirmationCodeEntity> findByCode(String code);
}
