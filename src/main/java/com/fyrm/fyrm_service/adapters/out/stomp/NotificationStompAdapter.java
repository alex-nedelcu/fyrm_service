package com.fyrm.fyrm_service.adapters.out.stomp;

import com.fyrm.fyrm_service.adapters.out.stomp.converter.StompNotificationConverter;
import com.fyrm.fyrm_service.application.port.out.SendNotificationPort;
import com.fyrm.fyrm_service.domain.Notification;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.web_socket.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@OutboundAdapter
@RequiredArgsConstructor
public class NotificationStompAdapter implements SendNotificationPort {

  private final SimpMessagingTemplate messagingTemplate;
  private final StompNotificationConverter stompNotificationConverter;

  @Override
  public void send(Notification notification) {
    var destination = Topic.NOTIFICATIONS.getName();
    var stompNotification = stompNotificationConverter.apply(notification);

    messagingTemplate.convertAndSendToUser(notification.getToUsername(), destination, stompNotification);
  }
}
