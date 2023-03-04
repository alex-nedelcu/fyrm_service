package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.RentConnection;

public interface PersistRentConnectionPort {

  Long persistAndFlush(RentConnection rentConnection);

  Long persist(RentConnection rentConnection);
}
