package com.fyrm.fyrm_service.adapters.in.web.converter.rentconnection;

import com.fyrm.fyrm_service.generatedapi.dtos.ProposedRentMateDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProposedRentMateConverter implements Converter<User, ProposedRentMateDto> {

  @Override
  public ProposedRentMateDto apply(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User must not be null for converting to dto");
    }

    return new ProposedRentMateDto()
        .userId(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .description(user.getDescription());
  }
}
