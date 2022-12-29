package com.fyrm.fyrm_service.adapters.in.web.mapper;

import com.fyrm.fyrm_service.domain.exception.base.BusinessExceptionType;
import com.fyrm.fyrm_service.generatedapi.dtos.BusinessExceptionDto.CodeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, typeConversionPolicy = ReportingPolicy.ERROR)
public interface BusinessExceptionTypeMapper {

  CodeEnum convert(BusinessExceptionType type);
}
