package com.fyrm.fyrm_service.adapters.in.web.converter;

import com.fyrm.fyrm_service.domain.User;
import com.fyrm.fyrm_service.generatedapi.dtos.UserDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoConverter implements Converter<User, UserDto> {

  @Override
  public UserDto apply(User user) {

    if (user == null) {
      throw new IllegalArgumentException("User must not be null for converting to dto!");
    }

    return new UserDto()
        .id(user.getId())
        .name(user.getName());
  }
}
