package live.aiotone.monitoring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Sector Domain Entity.
*/
@Table(name = "sector")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Sector {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "sector_name", nullable = false)
  private String sectorName;

  /**
  *  Sector 이름으로 Sector 객체를 생성하는 메서드.
   *
   * @param sectorName Sector 이름
   * @return Sector
  */
  public static Sector createByName(String sectorName) {
    return new Sector(null, sectorName);
  }
}
