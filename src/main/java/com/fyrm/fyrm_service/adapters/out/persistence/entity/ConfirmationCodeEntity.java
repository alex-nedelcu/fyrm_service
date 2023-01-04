package com.fyrm.fyrm_service.adapters.out.persistence.entity;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.base.Identifiable;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "confirmation_codes", schema = "public")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ConfirmationCodeEntity extends Identifiable {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "created_at", nullable = false)
  private ZonedDateTime createdAt;

  @Column(name = "expires_at", nullable = false)
  private ZonedDateTime expiresAt;

  @Column(name = "confirmed_at")
  private ZonedDateTime confirmedAt;
}
