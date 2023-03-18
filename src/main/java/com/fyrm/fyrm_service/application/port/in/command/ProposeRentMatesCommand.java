package com.fyrm.fyrm_service.application.port.in.command;

import com.fyrm.fyrm_service.infrastructure.validation.SelfValidating;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class ProposeRentMatesCommand extends SelfValidating<ProposeRentMatesCommand> {

  @NotNull(message = "error.validation.user.id.is.mandatory")
  Long initiatorId;

  @PositiveOrZero(message = "error.validation.rent.connection.proposal.maximum.size.must.be.positive")
  int proposalMaximumSize;

  @NotNull(message = "error.validation.rent.connection.search.profile.ids.are.mandatory")
  List<Long> searchProfileIds;

  public ProposeRentMatesCommand(Long initiatorId, int proposalMaximumSize, List<Long> searchProfileIds) {
    this.initiatorId = initiatorId;
    this.proposalMaximumSize = proposalMaximumSize;
    this.searchProfileIds = searchProfileIds;
    validateSelf();
  }
}
