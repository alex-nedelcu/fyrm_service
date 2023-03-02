package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.FindSearchProfilesByUserCommand;
import com.fyrm.fyrm_service.domain.SearchProfile;
import java.util.List;

public interface FindSearchProfilesByUserUseCase {

  List<SearchProfile> find(FindSearchProfilesByUserCommand findSearchProfilesByUserCommand);
}
