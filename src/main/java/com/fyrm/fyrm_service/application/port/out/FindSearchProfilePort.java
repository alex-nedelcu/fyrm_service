package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.SearchProfile;
import java.util.List;

public interface FindSearchProfilePort {

  List<SearchProfile> findAllByUserId(Long userId);
}
