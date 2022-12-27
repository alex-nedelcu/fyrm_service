package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.UserEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {

  List<UserEntity> findAllByNameContainsIgnoreCase(String name);
}
