package live.aiotone.monitoring.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import live.aiotone.monitoring.base.ServiceTestBase;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.Sensor;
import live.aiotone.monitoring.domain.SensorScore;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import live.aiotone.monitoring.repository.SensorScoreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

class LogServiceImplTest extends ServiceTestBase {

  @InjectMocks
  private LogServiceImpl logService;

  @Mock
  private SensorScoreRepository sensorScoreRepository;

  @Test
  void readErrorLogs() {
    // given
    Motor motor = TestFixtureFactory.createMotor();
    Sensor sensor = TestFixtureFactory.createSensor();
    SensorScore sensorScore = TestFixtureFactory.createSensorScore();
    ReflectionTestUtils.setField(sensorScore, "sensor", sensor);
    ReflectionTestUtils.setField(sensor, "motor", motor);
    when(sensorScoreRepository.findAllByScoreLessThanEqualOrderByTimeDesc(anyDouble(), any(Pageable.class))).thenReturn(
        new PageImpl<>(List.of(sensorScore)));
    // when
    logService.readErrorLogs(70, Pageable.unpaged());
    // then
    verify(sensorScoreRepository).findAllByScoreLessThanEqualOrderByTimeDesc(anyDouble(), any(Pageable.class));
  }
}