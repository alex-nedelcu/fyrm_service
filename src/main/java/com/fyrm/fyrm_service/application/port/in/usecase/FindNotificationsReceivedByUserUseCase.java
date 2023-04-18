package com.fyrm.fyrm_service.application.port.in.usecase;

import com.fyrm.fyrm_service.application.port.in.command.FindNotificationsReceivedByUserCommand;
import com.fyrm.fyrm_service.domain.Notification;
import java.util.List;

public interface FindNotificationsReceivedByUserUseCase {

  List<Notification> find(FindNotificationsReceivedByUserCommand findNotificationsReceivedByUserCommand);
}
