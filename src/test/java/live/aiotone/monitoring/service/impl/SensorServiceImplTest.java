package live.aiotone.monitoring.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.base.ServiceTestBase;
import live.aiotone.monitoring.common.exception.sensor.SensorNotFoundException;
import live.aiotone.monitoring.domain.SensorScore;
import live.aiotone.monitoring.repository.SensorRepository;
import live.aiotone.monitoring.repository.SensorScoreRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@SuppressWarnings("NonAsciiCharacters")
class SensorServiceImplTest extends ServiceTestBase {

  @InjectMocks
  SensorServiceImpl sensorService;

  @Mock
  SensorScoreRepository sensorScoreRepository;
  @Mock
  SensorRepository sensorRepository;

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
}