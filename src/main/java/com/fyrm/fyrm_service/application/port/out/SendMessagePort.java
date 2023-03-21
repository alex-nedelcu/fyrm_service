package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.ChatMessage;

public interface SendMessagePort {

  void sendToReceiverAndSender(ChatMessage chatMessage);
}
