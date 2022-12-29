package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.Account;
import java.util.List;

public interface FindAccountPort {

  List<Account> findAllByName(String name);
}
