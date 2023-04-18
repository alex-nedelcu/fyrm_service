package com.fyrm.fyrm_service.adapters.in.web.mapper;

import com.fyrm.fyrm_service.domain.Statistics;
import com.fyrm.fyrm_service.generatedapi.dtos.StatisticsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, typeConversionPolicy = ReportingPolicy.ERROR)
public interface StatisticsMapper {

  StatisticsDto map(Statistics statistics);
}
