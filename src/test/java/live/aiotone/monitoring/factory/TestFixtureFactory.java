package live.aiotone.monitoring.factory;

import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.Sector;

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
}
