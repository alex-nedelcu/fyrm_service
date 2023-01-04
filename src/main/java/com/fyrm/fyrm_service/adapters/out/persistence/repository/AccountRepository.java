package com.fyrm.fyrm_service.adapters.out.persistence.repository;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.AccountEntity;
import com.fyrm.fyrm_service.infrastructure.persistence.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<AccountEntity, Long> {

  List<AccountEntity> findAllByNameContainsIgnoreCase(String name);
}
