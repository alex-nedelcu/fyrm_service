package com.fyrm.fyrm_service.adapters.in.web;

import com.fyrm.fyrm_service.adapters.in.web.converter.notification.NotificationsReceivedByUserConverter;
import com.fyrm.fyrm_service.application.port.in.command.FindNotificationsReceivedByUserCommand;
import com.fyrm.fyrm_service.application.port.in.command.MarkAllNotificationsAsReadCommand;
import com.fyrm.fyrm_service.application.port.in.command.MarkNotificationAsReadCommand;
import com.fyrm.fyrm_service.application.port.in.usecase.FindNotificationsReceivedByUserUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.MarkAllNotificationsAsReadUseCase;
import com.fyrm.fyrm_service.application.port.in.usecase.MarkNotificationAsReadUseCase;
import com.fyrm.fyrm_service.generatedapi.NotificationsApi;
import com.fyrm.fyrm_service.generatedapi.dtos.NotificationsReceivedByUserDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.InboundAdapter;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.controller.FyrmApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FyrmApiController
@InboundAdapter
@RequiredArgsConstructor
public class NotificationApiController implements NotificationsApi {

  private final FindNotificationsReceivedByUserUseCase findNotificationsReceivedByUserUseCase;
  private final MarkNotificationAsReadUseCase markNotificationAsReadUseCase;
  private final MarkAllNotificationsAsReadUseCase markAllNotificationsAsReadUseCase;
  private final NotificationsReceivedByUserConverter notificationsReceivedByUserConverter;

  @Override
  public ResponseEntity<NotificationsReceivedByUserDto> findNotificationReceivedByUser(Long userId) {
    var command = new FindNotificationsReceivedByUserCommand(userId);
    var notifications = findNotificationsReceivedByUserUseCase.find(command);
    return ResponseEntity.ok(notificationsReceivedByUserConverter.apply(notifications));
  }

  @Override
  public ResponseEntity<Void> markAllNotificationsAsRead(Long userId) {
    var command = new MarkAllNotificationsAsReadCommand(userId);
    markAllNotificationsAsReadUseCase.markAll(command);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> markNotificationAsRead(Long notificationId) {
    var command = new MarkNotificationAsReadCommand(notificationId);
    markNotificationAsReadUseCase.mark(command);
    return ResponseEntity.noContent().build();
  }
}
