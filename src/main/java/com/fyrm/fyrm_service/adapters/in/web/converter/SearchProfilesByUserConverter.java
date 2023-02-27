package com.fyrm.fyrm_service.adapters.in.web.converter;

import com.fyrm.fyrm_service.domain.SearchProfile;
import com.fyrm.fyrm_service.generatedapi.dtos.SearchProfilesByUserDto;
import com.fyrm.fyrm_service.infrastructure.spring.mvc.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProfilesByUserConverter implements Converter<List<SearchProfile>, SearchProfilesByUserDto> {

  private final SearchProfileByUserConverter searchProfileByUserConverter;

  @Override
  public SearchProfilesByUserDto apply(List<SearchProfile> searchProfilesByUser) {
    if (searchProfilesByUser == null) {
      throw new IllegalArgumentException("Search profiles by user must not be null for converting to dto");
    }

    return new SearchProfilesByUserDto().searchProfiles(searchProfileByUserConverter.toList(searchProfilesByUser));
  }
}
