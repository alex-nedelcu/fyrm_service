package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.FindSearchProfilesByUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.FindSearchProfilesByUserUseCase;
import com.fyrm.fyrm_service.application.port.out.FindSearchProfilePort;
import com.fyrm.fyrm_service.application.port.out.FindUserPort;
import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.domain.exception.ResourceNotFoundException;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindSearchProfilesByUserService implements FindSearchProfilesByUserUseCase {

  private final FindUserPort findUserPort;
  private final FindSearchProfilePort findSearchProfilePort;

  @Override
  public List<SearchProfile> find(FindSearchProfilesByUserCommand findSearchProfilesByUserCommand) {
    User user = findUserPort.findById(findSearchProfilesByUserCommand.getUserId()).orElseThrow(ResourceNotFoundException::new);
    return findSearchProfilePort.findAllByUserId(user.getId());
  }
}
