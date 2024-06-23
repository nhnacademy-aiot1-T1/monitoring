package live.aiotone.monitoring.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Sector Domain Entity.
 */

@Entity
@Table(name = "sector")
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sector {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "sector_name", nullable = false)
  private String sectorName;

  @OneToMany(mappedBy = "sector")
  private List<Motor> motors;

  /**
   * Sector 이름으로 Sector 객체를 생성하는 메서드.
   *
   * @param sectorName Sector 이름
   * @return Sector
   */
  public static Sector createByName(String sectorName) {
    return Sector.builder()
        .sectorName(sectorName)
        .build();
  }

  /**
   * Sector 이름을 수정하는 메서드.
   *
   * @param sectorName Sector 이름
   */
  public void updateName(String sectorName) {
    this.sectorName = sectorName;
  }

}
