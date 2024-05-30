package live.aiotone.monitoring.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import live.aiotone.monitoring.base.ServiceTestBase;
import live.aiotone.monitoring.common.exception.sensor.SensorNotFoundException;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.Sensor;
import live.aiotone.monitoring.domain.SensorData;
import live.aiotone.monitoring.domain.SensorData.Duration;
import live.aiotone.monitoring.domain.SensorScore;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import live.aiotone.monitoring.repository.SensorDataRepository;
import live.aiotone.monitoring.repository.SensorRepository;
import live.aiotone.monitoring.repository.SensorScoreRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

@SuppressWarnings("NonAsciiCharacters")
class SensorServiceImplTest extends ServiceTestBase {

  @InjectMocks
  SensorServiceImpl sensorService;
  @Mock
  SensorScoreRepository sensorScoreRepository;
  @Mock
  SensorRepository sensorRepository;

  @Mock
  SensorDataRepository sensorDataRepository;

  @Nested
  class SensorScore_조회 {

    @Test
    void 존재하는_센서_점수를_조회할_때_SensorScoreList를_반환() {
      // given
      Long sensorId = 1L;
      LocalDateTime start = LocalDateTime.now().minusDays(1);
      LocalDateTime end = LocalDateTime.now();
      SensorScore sensorScore1 = SensorScore.builder()
          .id(sensorId)
          .score(1)
          .time(start)
          .build();
      SensorScore sensorScore2 = SensorScore.builder()
          .id(sensorId)
          .score(2)
          .time(end)
          .build();
      when(sensorRepository.existsById(sensorId)).thenReturn(true);
      when(sensorScoreRepository.findListSensorScoreBySensorIdAndTimeBetween(sensorId, start, end)).thenReturn(List.of(sensorScore1, sensorScore2));

      // when
      List<SensorScore> sensorScores = sensorService.readSensorScores(1L, sensorId, start, end);

      // then
      assertThat(sensorScores).hasSize(2)
          .contains(sensorScore1, sensorScore2);
      verify(sensorRepository, times(1)).existsById(sensorId);
      verify(sensorScoreRepository, times(1)).findListSensorScoreBySensorIdAndTimeBetween(sensorId, start, end);
    }

    @Test
    void 존재하지_않는_센서_점수를_조회할_때_SensorNotFoundException_발생() {
      // given
      Long sensorId = 1L;
      LocalDateTime start = LocalDateTime.now().minusDays(1);
      LocalDateTime end = LocalDateTime.now();
      when(sensorRepository.existsById(sensorId)).thenReturn(false);

      // when
      // then
      assertThatThrownBy(() -> sensorService.readSensorScores(1L, sensorId, start, end))
          .isInstanceOf(SensorNotFoundException.class);
      verify(sensorRepository, times(1)).existsById(sensorId);
      verify(sensorScoreRepository, times(0)).findListSensorScoreBySensorIdAndTimeBetween(sensorId, start, end);
    }

  }

  @Test
  void 존재하지_않는_센서_데이터를_조회할_때_SensorNotFoundException_발생() {
    // given
    Long sensorId = 1L;
    when(sensorRepository.findById(sensorId)).thenReturn(Optional.empty());

    // when
    // then
    assertThatThrownBy(() -> sensorService.readSensorData(1L, sensorId, Duration.DAY))
        .isInstanceOf(SensorNotFoundException.class);
    verify(sensorRepository, times(1)).findById(sensorId);
    verify(sensorDataRepository, times(0)).findAllSensorDataByDuration(anyString(), anyString(), any(Duration.class));
  }

  @Nested
  class SensorData_조회 {


    @Test
    void 조회_데이터를_반환() {
      // given
      Sensor testSensor = TestFixtureFactory.createSensor();
      Motor testMotor = TestFixtureFactory.createMotor();
      ReflectionTestUtils.setField(testSensor, "motor", testMotor);
      when(sensorRepository.findById(1L)).thenReturn(Optional.of(testSensor));
      when(sensorDataRepository.findAllSensorDataByDuration(anyString(), anyString(), any(Duration.class))).thenReturn(
          List.of(SensorData.builder().build()));
      // when
      List<SensorData> sensorData = sensorService.readSensorData(1L, 1L, Duration.DAY);
      // then
      assertThat(sensorData).hasSize(1);
    }
  }


}