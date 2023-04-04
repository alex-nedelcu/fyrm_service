package com.fyrm.fyrm_service.adapters.out.persistence;

import com.fyrm.fyrm_service.adapters.out.persistence.mapper.NotificationMapper;
import com.fyrm.fyrm_service.adapters.out.persistence.repository.NotificationRepository;
import com.fyrm.fyrm_service.application.port.out.PersistNotificationPort;
import com.fyrm.fyrm_service.domain.Notification;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.OutboundAdapter;
import lombok.RequiredArgsConstructor;

@OutboundAdapter
@RequiredArgsConstructor
public class NotificationAdapter implements PersistNotificationPort {

  private final NotificationRepository notificationRepository;
  private final NotificationMapper notificationMapper;

  @Override
  public Notification persist(Notification notification) {
    return notificationMapper.toDomain(notificationRepository.save(notificationMapper.toEntity(notification)));
  }
}
