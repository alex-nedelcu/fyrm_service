package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.NotificationEntity;
import com.fyrm.fyrm_service.domain.Notification;

@DomainEntityMapper
public class NotificationMapper implements IDomainEntityMapper<NotificationEntity, Notification> {

  @Override
  public NotificationEntity toEntity(Notification notification) {
    return NotificationEntity.builder()
        .id(notification.getId())
        .preview(notification.getPreview())
        .fromId(notification.getFromId())
        .toId(notification.getToId())
        .fromUsername(notification.getFromUsername())
        .toUsername(notification.getToUsername())
        .isRead(notification.isRead())
        .sentAt(notification.getSentAt())
        .build();
  }

  @Override
  public Notification toDomain(NotificationEntity notificationEntity) {
    return Notification.builder()
        .id(notificationEntity.getId())
        .preview(notificationEntity.getPreview())
        .fromId(notificationEntity.getFromId())
        .toId(notificationEntity.getToId())
        .fromUsername(notificationEntity.getFromUsername())
        .toUsername(notificationEntity.getToUsername())
        .isRead(notificationEntity.isRead())
        .sentAt(notificationEntity.getSentAt())
        .build();
  }
}
