package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.SearchProfile;

public interface PersistSearchProfilePort {

  SearchProfile persist(SearchProfile searchProfile);
}
