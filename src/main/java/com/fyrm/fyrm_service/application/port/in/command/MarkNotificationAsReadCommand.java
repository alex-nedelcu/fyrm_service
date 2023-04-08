package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class MarkNotificationAsReadCommand extends SelfValidating<MarkNotificationAsReadCommand> {

  @NotNull(message = "error.validation.notification.id.is.mandatory")
  Long notificationId;

  public MarkNotificationAsReadCommand(Long notificationId) {
    this.notificationId = notificationId;
    validateSelf();
  }
}
