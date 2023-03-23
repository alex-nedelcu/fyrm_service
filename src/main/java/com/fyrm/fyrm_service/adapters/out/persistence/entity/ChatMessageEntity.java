package com.fyrm.fyrm_service.adapters.out.persistence.entity;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.base.Auditable;
import java.time.ZonedDateTime;
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
@Table(name = "chat_messages", schema = "public")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ChatMessageEntity extends Auditable {

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "from_id", nullable = false)
  private Long fromId;

  @Column(name = "to_id", nullable = false)
  private Long toId;

  @Column(name = "from_username", nullable = false)
  private String fromUsername;

  @Column(name = "to_username", nullable = false)
  private String toUsername;

  @Column(name = "sent_at", nullable = false)
  private ZonedDateTime sentAt;
}
