package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.domain.RentConnectionStatus;
import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class UpdateRentConnectionCommand extends SelfValidating<UpdateRentConnectionCommand> {

  @NotNull(message = "error.validation.rent.connection.status.is.mandatory")
  RentConnectionStatus status;

  public UpdateRentConnectionCommand(RentConnectionStatus status) {
    this.status = status;
    validateSelf();
  }
}
