package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.Notification;

public interface SendNotificationPort {

  void send(Notification notification);
}
