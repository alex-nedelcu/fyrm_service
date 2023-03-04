package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.RentConnection;
import java.util.Optional;

public interface FindRentConnectionPort {

  Optional<RentConnection> findById(Long id);

  Optional<RentConnection> findLatestActiveByUserId(Long userId);

  boolean hasAnyActive(Long userId);
}
