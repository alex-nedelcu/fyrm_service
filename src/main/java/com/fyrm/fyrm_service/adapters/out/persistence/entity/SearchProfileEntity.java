package com.fyrm.fyrm_service.adapters.out.persistence.entity;

import com.fyrm.fyrm_service.adapters.out.persistence.entity.base.Auditable;
import com.fyrm.fyrm_service.infrastructure.spring.security.model.User;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "search_profiles", schema = "public")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SearchProfileEntity extends Auditable {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "rent_price_lower_bound", nullable = false)
  private double rentPriceLowerBound;

  @Column(name = "rent_price_upper_bound", nullable = false)
  private double rentPriceUpperBound;

  @Column(name = "latitude", nullable = false)
  private double latitude;

  @Column(name = "longitude", nullable = false)
  private double longitude;

  @ElementCollection
  @CollectionTable(name = "search_profile_gender", joinColumns = @JoinColumn(name = "search_profile_id"))
  @Column(name = "rent_mates_gender_options", nullable = false)
  private List<String> rentMatesGenderOptions;

  @ElementCollection
  @CollectionTable(name = "search_profile_rent_mate_count", joinColumns = @JoinColumn(name = "search_profile_id"))
  @Column(name = "rent_mate_count_options", nullable = false)
  private List<String> rentMateCountOptions;

  @ElementCollection
  @CollectionTable(name = "search_profile_bedroom_options", joinColumns = @JoinColumn(name = "search_profile_id"))
  @Column(name = "bedroom_options", nullable = false)
  private List<String> bedroomOptions;

  @ElementCollection
  @CollectionTable(name = "search_profile_bathroom_options", joinColumns = @JoinColumn(name = "search_profile_id"))
  @Column(name = "bathroom_options", nullable = false)
  private List<String> bathroomOptions;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "used_search_profiles",
      joinColumns = @JoinColumn(name = "search_profile_id"),
      inverseJoinColumns = @JoinColumn(name = "rent_connection_id")
  )
  private List<RentConnectionEntity> rentConnections;
}
