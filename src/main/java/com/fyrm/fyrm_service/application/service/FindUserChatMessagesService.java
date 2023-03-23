package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.FindUserChatMessagesCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.FindUserChatMessagesUseCase;
import com.fyrm.fyrm_service.application.port.out.FindMessagePort;
import com.fyrm.fyrm_service.domain.ChatMessage;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindUserChatMessagesService implements FindUserChatMessagesUseCase {

  private final FindMessagePort findMessagePort;

  @Override
  public List<ChatMessage> find(FindUserChatMessagesCommand findUserChatMessagesCommand) {
    return findMessagePort.findAllSentOrReceivedBy(findUserChatMessagesCommand.getUserId());
  }
}
