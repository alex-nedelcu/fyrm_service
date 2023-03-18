package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.ProposeRentMatesCommand;
import com.fyrm.fyrm_service.domain.RentMateProposal;

public interface ProposeRentMatesUseCase {

  RentMateProposal propose(ProposeRentMatesCommand proposeRentMatesCommand);
}
