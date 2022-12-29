package com.fyrm.fyrm_service.adapters.out.persistence.entity;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.base.Identifiable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "accounts", schema = "public")
@SuperBuilder
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AccountEntity extends Identifiable {

  @Column(name = "name")
  private String name;
}
