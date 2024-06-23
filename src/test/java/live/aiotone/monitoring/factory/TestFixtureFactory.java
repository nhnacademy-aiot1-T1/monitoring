package live.aiotone.monitoring.factory;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import live.aiotone.monitoring.controller.dto.response.MotorOverviewResponse;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.MotorOverview;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.domain.SectorOverView;
import live.aiotone.monitoring.domain.Sensor;
import live.aiotone.monitoring.domain.SensorScore;

public class TestFixtureFactory {

  static Long motorId = 0L;
  static Long sectorId = 0L;

  public static Motor createMotorWithSector() {
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

  public static Motor createMotor() {
    motorId++;
    return Motor.builder()
        .deviceName("deviceName" + motorId)
        .motorName("motorName" + motorId)
        .on(true)
        .normal(true)
        .build();
  }

  public static Sensor createSensor() {
    return Sensor.builder()
        .sensorName("sensorName")
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
        .motors(List.of(createMotorWithSector(), createMotorWithSector()))
        .build();
  }

  public static SensorScore createSensorScore() {
    return SensorScore.builder()
        .time(LocalDateTime.now())
        .score(100)
        .build();
  }

  public static MotorRunningRateDto createMotorRunningRateDto() {
    String timestamp = "2022-01-01T00:00:00";
    double rate = 0.75;
    return new MotorRunningRateDto(timestamp, rate);
  }

  public static MotorOverview createMotorOverview() {
    return MotorOverview.builder()
        .totalMotorCount(10)
        .totalOnMotorCount(7)
        .totalNormalMotorCount(8)
        .build();
  }

  public static MotorOverviewResponse createMotorOverviewResponseFromMotorOverview(MotorOverview motorOverview) {
    return MotorOverviewResponse.builder()
        .totalMotorCount(motorOverview.getTotalMotorCount())
        .totalOnMotorCount(motorOverview.getTotalOnMotorCount())
        .totalNormalMotorCount(motorOverview.getTotalNormalMotorCount())
        .build();
  }
}
