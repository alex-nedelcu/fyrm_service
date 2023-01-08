package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;

public interface PersistUserPort {

  User persist(User user);
}
