package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.ProcessMessageCommand;

public interface ProcessMessageUseCase {

  void process(ProcessMessageCommand processMessageCommand);
}
