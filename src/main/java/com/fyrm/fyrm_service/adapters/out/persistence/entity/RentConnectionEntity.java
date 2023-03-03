package com.fyrm.fyrm_service.adapters.out.persistence.entity;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.base.Identifiable;
import com.fyrm.fyrm_service.domain.RentConnectionStatus;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "rent_connections", schema = "public")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RentConnectionEntity extends Identifiable {

  @Column(name = "initiator_id", nullable = false)
  private Long initiatorId;

  @Column(name = "proposal_maximum_size", nullable = false)
  private int proposalMaximumSize;

  @Column(name = "status", nullable = false)
  private RentConnectionStatus status;

  @ManyToMany(mappedBy = "rentConnections")
  private List<SearchProfileEntity> searchProfiles;
}
