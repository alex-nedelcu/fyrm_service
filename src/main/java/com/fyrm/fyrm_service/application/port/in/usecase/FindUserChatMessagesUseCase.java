package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.FindUserChatMessagesCommand;
import com.fyrm.fyrm_service.domain.ChatMessage;
import java.util.List;

public interface FindUserChatMessagesUseCase {

  List<ChatMessage> find(FindUserChatMessagesCommand findUserChatMessagesCommand);
}
