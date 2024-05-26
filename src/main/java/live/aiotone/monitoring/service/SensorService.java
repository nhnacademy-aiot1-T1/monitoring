package live.aiotone.monitoring.service;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.domain.SensorScore;

/**
 * 센서 Service.
 */
public interface SensorService {

  /**
   * 조회 기간 사이 모터 센서 점수를 조회.
   *
   * @param motorId  센서 점수를 조회할 모터 아이디
   * @param sensorId 센서 아이디
   * @param start    조회 시작일
   * @param end      조회 종료일
   */
  List<SensorScore> readSensorScores(Long motorId, Long sensorId, LocalDateTime start, LocalDateTime end);

}
