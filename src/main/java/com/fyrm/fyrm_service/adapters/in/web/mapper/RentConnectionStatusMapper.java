package com.fyrm.fyrm_service.adapters.in.web.mapper;

import com.fyrm.fyrm_service.domain.RentConnectionStatus;
import com.fyrm.fyrm_service.generatedapi.dtos.UpdateRentConnectionDto.RentConnectionStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, typeConversionPolicy = ReportingPolicy.ERROR)
public interface RentConnectionStatusMapper {

  RentConnectionStatus toDomain(RentConnectionStatusEnum rentConnectionStatusEnum);

  RentConnectionStatusEnum toGeneratedEnum(RentConnectionStatus rentConnectionStatus);
}
