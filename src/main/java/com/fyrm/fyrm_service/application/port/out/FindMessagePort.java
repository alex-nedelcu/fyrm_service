package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.ChatMessage;
import java.util.List;

public interface FindMessagePort {

  List<ChatMessage> findAllSentOrReceivedBy(Long userId);
}
