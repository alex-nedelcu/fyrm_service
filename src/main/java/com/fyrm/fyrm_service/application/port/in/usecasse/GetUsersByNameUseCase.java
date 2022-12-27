package com.fyrm.fyrm_service.application.port.in.usecasse;

import com.fyrm.fyrm_service.application.port.in.command.GetUsersByNameCommand;
import com.fyrm.fyrm_service.domain.User;
import java.util.List;

public interface GetUsersByNameUseCase {

  List<User> get(GetUsersByNameCommand getUsersByNameCommand);
}
