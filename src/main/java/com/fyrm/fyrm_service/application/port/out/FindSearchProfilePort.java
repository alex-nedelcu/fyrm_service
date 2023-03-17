package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.util.List;
import java.util.Optional;

public interface FindSearchProfilePort {

  Optional<SearchProfile> findById(Long id);

  List<SearchProfile> findAllByUserId(Long userId);

  List<SearchProfile> findAllByIds(List<Long> ids);

  List<SearchProfile> findAllByUsers(List<User> users);
}
