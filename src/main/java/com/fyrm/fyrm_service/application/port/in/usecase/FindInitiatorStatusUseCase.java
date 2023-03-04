package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.FindInitiatorStatusCommand;
import com.fyrm.fyrm_service.domain.InitiatorCurrentState;

public interface FindInitiatorStatusUseCase {

  InitiatorCurrentState findStatus(FindInitiatorStatusCommand findInitiatorStatusCommand);
}
