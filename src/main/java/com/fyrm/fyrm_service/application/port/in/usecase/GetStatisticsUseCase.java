package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.GetStatisticsByUserCommand;
import com.fyrm.fyrm_service.domain.Statistics;

public interface GetStatisticsUseCase {

  Statistics getByUser(GetStatisticsByUserCommand getStatisticsByUserCommand);
}
