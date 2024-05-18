package live.aiotone.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import live.aiotone.monitoring.base.RepositoryTestBase;
import live.aiotone.monitoring.domain.SensorScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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


}
