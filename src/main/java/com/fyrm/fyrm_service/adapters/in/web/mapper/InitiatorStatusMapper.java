package com.fyrm.fyrm_service.adapters.in.web.mapper;

import com.fyrm.fyrm_service.domain.InitiatorStatus;
import com.fyrm.fyrm_service.generatedapi.dtos.InitiatorStatusDto.InitiatorStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, typeConversionPolicy = ReportingPolicy.ERROR)
public interface InitiatorStatusMapper {

  InitiatorStatus toDomain(InitiatorStatusEnum initiatorStatusEnum);

  InitiatorStatusEnum toGeneratedEnum(InitiatorStatus initiatorStatus);
}
