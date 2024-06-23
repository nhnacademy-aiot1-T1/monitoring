package live.aiotone.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.base.RepositoryTestBase;
import live.aiotone.monitoring.domain.Sensor;
import live.aiotone.monitoring.domain.SensorScore;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@SuppressWarnings("NonAsciiCharacters")
public class SensorScoreRepositoryTest extends RepositoryTestBase {

  @Autowired
  private SensorScoreRepository sensorScoreRepository;

  @BeforeEach
  void setup() {
    sensorScoreRepository.deleteAll();
    testEntityManager.getEntityManager()
        .createNativeQuery("ALTER TABLE sensor_score AUTO_INCREMENT = 1").executeUpdate();
  }


  @Nested
  class SensorScore_save_테스트 {

    @Test
    void sensorScore_save() {
      // given
      SensorScore sensorScore = SensorScore.builder()
          .id(1L)
          .score(1)
          .time(LocalDateTime.now())
          .build();

      // when
      SensorScore savedSensorScore = sensorScoreRepository.save(sensorScore);

      // then
      assertThat(savedSensorScore).usingRecursiveComparison().isEqualTo(sensorScore);
    }
  }


  @Nested
  class SensorScore_조회_테스트 {

    @Test
    void sensorScore_조회() {
      // given
      LocalDateTime start = LocalDateTime.now().minusDays(1);
      LocalDateTime end = LocalDateTime.now();
      Sensor testSensor = TestFixtureFactory.createSensor();
      testEntityManager.persist(testSensor);
      SensorScore sensorScore1 = SensorScore.builder()
          .score(1)
          .sensor(testSensor)
          .time(LocalDateTime.now().minusMinutes(1))
          .build();
      SensorScore sensorScore2 = SensorScore.builder()
          .score(2)
          .sensor(testSensor)
          .time(LocalDateTime.now().minusMinutes(5))
          .build();
      testEntityManager.persist(sensorScore1);
      testEntityManager.persist(sensorScore2);

      // when
      List<SensorScore> sensorScores = sensorScoreRepository.findListSensorScoreBySensorIdAndTimeBetween(testSensor.getId(), start, end);

      // then
      assertThat(sensorScores)
          .hasSize(2)
          .contains(sensorScore1, sensorScore2);
    }

    @ParameterizedTest
    @CsvSource({"0.5, 2", "0.1, 0", "1, 2"})
    void 이상_점수가_주어질_때_이상_점수_이하인_점수_조회(double score, int expectedCount) {
      // given
      SensorScore sensorScore1 = SensorScore.builder()
          .score(0.5).time(LocalDateTime.now().minusMinutes(1))
          .build();
      SensorScore sensorScore2 = SensorScore.builder()
          .score(0.6)
          .time(LocalDateTime.now().minusMinutes(30))
          .build();
      SensorScore sensorScore3 = SensorScore.builder()
          .score(0.4)
          .time(LocalDateTime.now().minusMinutes(10))
          .build();
      testEntityManager.persist(sensorScore1);
      testEntityManager.persist(sensorScore2);
      testEntityManager.persist(sensorScore3);

      // when
      List<SensorScore> sensorScores = sensorScoreRepository.findAllByScoreLessThanEqualOrderByTimeDesc(score, PageRequest.of(0, 2)).getContent();

      // then
      assertThat(sensorScores)
          .hasSize(expectedCount);
    }
  }


}
