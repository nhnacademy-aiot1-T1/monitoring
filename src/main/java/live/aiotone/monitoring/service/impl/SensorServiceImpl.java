package live.aiotone.monitoring.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.common.exception.sensor.SensorNotFoundException;
import live.aiotone.monitoring.domain.Sensor;
import live.aiotone.monitoring.domain.SensorData;
import live.aiotone.monitoring.domain.SensorData.Duration;
import live.aiotone.monitoring.domain.SensorScore;
import live.aiotone.monitoring.repository.SensorDataRepository;
import live.aiotone.monitoring.repository.SensorRepository;
import live.aiotone.monitoring.repository.SensorScoreRepository;
import live.aiotone.monitoring.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SensorService 구현체.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorServiceImpl implements SensorService {

  private final SensorScoreRepository sensorScoreRepository;
  private final SensorRepository sensorRepository;
  private final SensorDataRepository sensorDataRepository;

  @Override
  public List<SensorScore> readSensorScores(Long motorId, Long sensorId, LocalDateTime start, LocalDateTime end) {
    if (!sensorRepository.existsById(sensorId)) {
      throw new SensorNotFoundException(sensorId);
    }
    return sensorScoreRepository.findListSensorScoreBySensorIdAndTimeBetween(sensorId, start, end);
  }

  @Override
  public List<SensorData> readSensorData(Long deviceId, Long sensorId, Duration duration) {
    Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new SensorNotFoundException(sensorId));
    String motorDeviceName = sensor.getMotor().getDeviceName();
    String sensorName = sensor.getSensorName();
    return sensorDataRepository.findAllSensorDataByDuration(motorDeviceName, sensorName, duration);
  }
}
