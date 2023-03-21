package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class ProcessMessageCommand extends SelfValidating<ProcessMessageCommand> {

  @NotEmpty(message = "error.validation.content.is.mandatory")
  String content;
  @NotNull(message = "error.validation.user.id.is.mandatory")
  Long fromId;
  @NotEmpty(message = "error.validation.username.is.mandatory")
  String fromUsername;
  @NotNull(message = "error.validation.user.id.is.mandatory")
  Long toId;
  @NotEmpty(message = "error.validation.username.is.mandatory")
  String toUsername;

  public ProcessMessageCommand(String content, Long fromId, String fromUsername, Long toId, String toUsername) {
    this.content = content;
    this.fromId = fromId;
    this.fromUsername = fromUsername;
    this.toId = toId;
    this.toUsername = toUsername;
    validateSelf();
  }
}
