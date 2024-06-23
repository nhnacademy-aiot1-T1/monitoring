package live.aiotone.monitoring.domain;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Sector 개요 도메인 클래스.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class SectorOverView {

  private final long sectorId;
  private final String sectorName;
  private final int totalCount;
  private final int normalCount;
  private final int isOnCount;

  /**
   * SectorList 로 SectorOverViewList 생성.
   *
   * @param sectors Sector
   * @return SectorOverView
   */
  public static List<SectorOverView> fromSectorList(List<Sector> sectors) {
    return sectors.stream()
        .map(SectorOverView::fromSector)
        .collect(Collectors.toList());
  }

  /**
   * Sector 객체로 SectorOverView 객체를 생성.
   *
   * @param sector Sector
   * @return SectorOverView
   */
  public static SectorOverView fromSector(Sector sector) {
    final int totalCount = sector.getMotors().size();
    int normalCount = 0;
    int isOnCount = 0;

    for (Motor motor : sector.getMotors()) {
      if (motor.isNormal()) {
        normalCount++;
      }
      if (motor.isOn()) {
        isOnCount++;
      }
    }

    return SectorOverView.builder()
        .sectorId(sector.getId())
        .sectorName(sector.getSectorName())
        .totalCount(totalCount)
        .normalCount(normalCount)
        .isOnCount(isOnCount)
        .build();
  }
}
