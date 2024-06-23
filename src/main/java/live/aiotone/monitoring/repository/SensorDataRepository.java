package live.aiotone.monitoring.repository;

import java.util.List;
import live.aiotone.monitoring.domain.SensorData;
import live.aiotone.monitoring.domain.SensorData.Duration;

/**
 * 센서 데이터 Repository.
 */
public interface SensorDataRepository {

  /**
   * 해당  deviceName(모터) 에 대한 센서 데이터 조회.
   *
   * @param deviceName 해당 모터의  deviceName
   * @param duration   현재를 기준으로 조회 시작 기간 //   * @param period     데이터 간격 //   * @param unit       조회 기간 단위
   * @return 조회된 센서 데이터 리스트
   */
  List<SensorData> findAllSensorDataByDuration(String deviceName, String channel, Duration duration);

}