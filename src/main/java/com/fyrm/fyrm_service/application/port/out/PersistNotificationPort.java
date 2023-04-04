package com.fyrm.fyrm_service.application.port.out;

import com.fyrm.fyrm_service.domain.Notification;

public interface PersistNotificationPort {

  Notification persist(Notification notification);
}
