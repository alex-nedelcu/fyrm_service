package com.fyrm.fyrm_service.adapters.in.web.converter.notification;

import com.fyrm.fyrm_service.domain.Notification;
import com.fyrm.fyrm_service.generatedapi.dtos.FetchedNotificationDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class FetchedNotificationConverter implements Converter<Notification, FetchedNotificationDto> {

  @Override
  public FetchedNotificationDto apply(Notification notification) {
    if (notification == null) {
      throw new IllegalArgumentException("Notification must not be null for converting to dto");
    }

    return new FetchedNotificationDto()
        .id(notification.getId())
        .preview(notification.getPreview())
        .fromId(notification.getFromId())
        .fromUsername(notification.getFromUsername())
        .toId(notification.getToId())
        .toUsername(notification.getToUsername())
        .isRead(notification.isRead())
        .sentOnDayMonthYearFormat(toDayMonthYearFormat(notification.getSentAt()));
  }

  private String toDayMonthYearFormat(ZonedDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
