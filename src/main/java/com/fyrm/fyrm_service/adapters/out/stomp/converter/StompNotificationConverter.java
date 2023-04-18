package com.fyrm.fyrm_service.adapters.out.stomp.converter;

import com.fyrm.fyrm_service.adapters.out.stomp.dto.StompNotification;
import com.fyrm.fyrm_service.domain.Notification;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompNotificationConverter implements Converter<Notification, StompNotification> {

  @Override
  public StompNotification apply(Notification notification) {
    if (notification == null) {
      throw new IllegalArgumentException("Notification must not be null for converting to stomp notification");
    }

    return StompNotification.builder()
        .id(notification.getId())
        .preview(notification.getPreview())
        .fromId(notification.getFromId())
        .fromUsername(notification.getFromUsername())
        .toId(notification.getToId())
        .toUsername(notification.getToUsername())
        .isRead(notification.isRead())
        .sentOnDayMonthYearFormat(toDayMonthYearFormat(notification.getSentAt()))
        .build();
  }

  private String toDayMonthYearFormat(ZonedDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
