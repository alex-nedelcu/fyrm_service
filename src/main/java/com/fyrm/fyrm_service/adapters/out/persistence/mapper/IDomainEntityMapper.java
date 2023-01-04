package com.fyrm.fyrm_service.adapters.out.persistence.mapper;

import java.util.List;

public interface IDomainEntityMapper<ENTITY_TYPE, DOMAIN_TYPE> {

  ENTITY_TYPE toEntity(DOMAIN_TYPE domainObject);

  default List<ENTITY_TYPE> toEntityList(List<DOMAIN_TYPE> domainList) {
    return domainList.stream().map(this::toEntity).toList();
  }

  DOMAIN_TYPE toDomain(ENTITY_TYPE entityObject);

  default List<DOMAIN_TYPE> toDomainList(List<ENTITY_TYPE> entityList) {
    return entityList.stream().map(this::toDomain).toList();
  }
}
