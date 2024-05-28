package live.aiotone.monitoring.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 모터 Entity.
 */

@Entity
@Table(name = "motor")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Motor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "sector_id")
  @ManyToOne(fetch = FetchType.EAGER)
  private Sector sector;

  @Column(name = "device_name", nullable = false)
  private String deviceName;

  @Column(name = "motor_name", nullable = false)
  private String motorName;

  @Column(name = "is_on", nullable = false)
  private boolean on;

  @Column(name = "is_normal")
  private boolean normal;

  @OneToMany(mappedBy = "motor")
  private List<Sensor> sensors;

}
