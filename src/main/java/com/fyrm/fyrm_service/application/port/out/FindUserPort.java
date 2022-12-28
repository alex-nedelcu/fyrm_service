package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.User;
import java.util.List;

public interface FindUserPort {

  List<User> findAllByName(String name);
}
