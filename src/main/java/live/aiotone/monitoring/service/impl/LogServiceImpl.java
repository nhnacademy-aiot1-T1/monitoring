package live.aiotone.monitoring.service.impl;

import java.util.ArrayList;
import java.util.List;
import live.aiotone.monitoring.controller.dto.response.ErrorLogResponse;
import live.aiotone.monitoring.controller.dto.response.ErrorLogResponse.ErrorLog;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.Sensor;
import live.aiotone.monitoring.domain.SensorScore;
import live.aiotone.monitoring.repository.SensorScoreRepository;
import live.aiotone.monitoring.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 로그 서비스 구현체.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

  private final SensorScoreRepository sensorScoreRepository;

  @Override
  public ErrorLogResponse readErrorLogs(double score, Pageable pageable) {

    Page<SensorScore> sensorScoresPage = sensorScoreRepository.findAllByScoreLessThanEqualOrderByTimeDesc(score, pageable);
    List<SensorScore> sensorScoreList = sensorScoresPage.getContent();
    List<ErrorLog> errorLogList = new ArrayList<>();
    for (SensorScore sensorScore : sensorScoreList) {
      Motor motor = sensorScore.getSensor().getMotor();
      Sensor sensor = sensorScore.getSensor();
      ErrorLog errorLog = ErrorLog.builder()
          .motorId(sensor.getId())
          .motorName(motor.getMotorName())
          .sensorType(sensor.getSensorName())
          .score(sensorScore.getScore())
          .time(sensorScore.getTime())
          .build();
      errorLogList.add(errorLog);
    }
    return ErrorLogResponse.builder()
        .total(sensorScoresPage.getTotalPages())
        .page(sensorScoresPage.getNumber())
        .size(sensorScoresPage.getSize())
        .logs(errorLogList)
        .build();
  }
}
