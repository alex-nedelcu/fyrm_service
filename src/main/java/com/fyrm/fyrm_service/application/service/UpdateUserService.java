package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.UpdateUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.UpdateUserUseCase;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.application.port.out.PersistUserPort;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

  private final PersistUserPort persistUserPort;
  private final FindUserPort findUserPort;

  @Override
  public void update(UpdateUserCommand updateUserCommand) {
    Long userId = updateUserCommand.getUserId();
    User user = findUserPort.findById(userId).orElseThrow(
        () -> new ResourceNotFoundException("User with id " + userId + " not found")
    );

    user.setDescription(updateUserCommand.getDescription());
    user.setIsSearching(updateUserCommand.getIsSearching());
    persistUserPort.persist(user);
  }
}
