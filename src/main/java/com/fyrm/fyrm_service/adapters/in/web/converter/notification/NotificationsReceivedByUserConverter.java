package com.fyrm.fyrm_service.adapters.in.web.converter.notification;

import com.fyrm.fyrm_service.domain.Notification;
import com.fyrm.fyrm_service.generatedapi.dtos.NotificationsReceivedByUserDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationsReceivedByUserConverter implements Converter<List<Notification>, NotificationsReceivedByUserDto> {

  private final FetchedNotificationConverter fetchedNotificationConverter;

  @Override
  public NotificationsReceivedByUserDto apply(List<Notification> notifications) {
    if (notifications == null) {
      throw new IllegalArgumentException("Notifications not be null for converting to dto");
    }

    return new NotificationsReceivedByUserDto().notifications(fetchedNotificationConverter.toList(notifications));
  }
}
