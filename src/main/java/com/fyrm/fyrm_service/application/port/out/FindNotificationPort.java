package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.Notification;
import java.util.List;

public interface FindNotificationPort {

  List<Notification> findAllReceivedBy(Long userId);
}
