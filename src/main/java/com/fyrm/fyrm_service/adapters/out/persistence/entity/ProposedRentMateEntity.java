package com.fyrm.fyrm_service.adapters.out.persistence.entity;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.base.Auditable;
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
@Table(name = "proposed_rent_mates", schema = "public")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProposedRentMateEntity extends Auditable {

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "rent_connection_id", nullable = false)
  private Long rentConnectionId;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "description")
  private String description;
}
