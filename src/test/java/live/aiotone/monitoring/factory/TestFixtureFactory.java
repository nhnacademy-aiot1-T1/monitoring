package live.aiotone.monitoring.factory;

import java.util.List;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.domain.SectorOverView;

public class TestFixtureFactory {

  static Long motorId = 0L;
  static Long sectorId = 0L;

  public static Motor createMotor() {
    motorId++;
    return Motor.builder()
        .id(motorId)
        .sector(createSector())
        .deviceName("deviceName" + motorId)
        .motorName("motorName" + motorId)
        .on(true)
        .normal(true)
        .build();
  }


  public static Sector createSector() {
    sectorId++;
    return Sector.builder()
        .id(sectorId)
        .sectorName("sectorName" + sectorId)
        .build();
  }

  public static SectorOverView createSectorOverView() {
    sectorId++;
    return SectorOverView.builder()
        .sectorId(sectorId)
        .sectorName("sectorName" + sectorId)
        .totalCount(2)
        .normalCount(1)
        .isOnCount(1)
        .build();
  }

  public static Sector createSectorWithMotor() {
    sectorId++;
    return Sector.builder()
        .id(sectorId)
        .sectorName("sectorName" + sectorId)
        .motors(List.of(createMotor(), createMotor()))
        .build();
  }

}
