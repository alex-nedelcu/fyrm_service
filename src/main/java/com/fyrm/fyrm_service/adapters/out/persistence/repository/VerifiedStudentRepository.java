package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.VerifiedStudentEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifiedStudentRepository extends BaseRepository<VerifiedStudentEntity, Long> {

  Optional<VerifiedStudentEntity> findByEmail(String email);
}
