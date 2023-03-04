package com.fyrm.fyrm_service.adapters.in.web.converter.rentconnection;

import com.fyrm.fyrm_service.domain.ProposedRentMate;
import com.fyrm.fyrm_service.generatedapi.dtos.ProposedRentMateDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProposedRentMateConverter implements Converter<ProposedRentMate, ProposedRentMateDto> {

  @Override
  public ProposedRentMateDto apply(ProposedRentMate proposedRentMate) {
    if (proposedRentMate == null) {
      throw new IllegalArgumentException("Proposed rent mate must not be null for converting to dto");
    }

    return new ProposedRentMateDto()
        .userId(proposedRentMate.getUserId())
        .username(proposedRentMate.getUsername())
        .email(proposedRentMate.getEmail())
        .description(proposedRentMate.getDescription());
  }
}
