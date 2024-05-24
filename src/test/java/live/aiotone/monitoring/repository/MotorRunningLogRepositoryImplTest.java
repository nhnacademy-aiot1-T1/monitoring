package live.aiotone.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.base.RepositoryTestBase;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.MotorRunningLog;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class MotorRunningLogRepositoryImplTest extends RepositoryTestBase {

  @Autowired
  MotorRunningLogRepository motorRunningLogRepository;

  @BeforeEach
  void setup() {
    motorRunningLogRepository.deleteAll();
    testEntityManager.getEntityManager()
        .createNativeQuery("ALTER TABLE motor_running_log AUTO_INCREMENT = 1").executeUpdate();
  }

  @Nested
  class 총_모터_가동률_조회_테스트 {

    @Test
    void 최근_일주일_동안_모터_가동률_조회() {
      LocalDateTime localDateTime = LocalDateTime.of(2024, 5, 20, 0, 0, 0);
      MotorRunningLog motorRunningLog = MotorRunningLog.builder().createdAt(localDateTime).on(true)
          .build();
      MotorRunningLog motorRunningLog2 = MotorRunningLog.builder()
          .createdAt(localDateTime.plusDays(1)).on(false).build();
      MotorRunningLog motorRunningLog3 = MotorRunningLog.builder()
          .createdAt(localDateTime.plusDays(2)).on(true).build();
      testEntityManager.persist(motorRunningLog);
      testEntityManager.persist(motorRunningLog2);
      testEntityManager.persist(motorRunningLog3);

      List<MotorRunningRateDto> rateDtoList = motorRunningLogRepository.readMotorRunningRate(
          localDateTime.plusDays(3), Duration.WEEK);

      assertThat(rateDtoList.size()).isEqualTo(3);

      assertThat(rateDtoList.get(0).getRate()).isEqualTo(100.0);
      assertThat(rateDtoList.get(1).getRate()).isEqualTo(0.0);
      assertThat(rateDtoList.get(2).getRate()).isEqualTo(100.0);
    }

    @Test
    void 최근_24시간_동안_모터_가동률_조회() {
      LocalDateTime localDateTime = LocalDateTime.of(2024, 5, 20, 0, 0, 0);
      MotorRunningLog motorRunningLog = MotorRunningLog.builder().createdAt(localDateTime).on(true)
          .build();
      MotorRunningLog motorRunningLog2 = MotorRunningLog.builder()
          .createdAt(localDateTime.plusHours(1)).on(false).build();
      MotorRunningLog motorRunningLog3 = MotorRunningLog.builder()
          .createdAt(localDateTime.plusHours(2)).on(true).build();
      testEntityManager.persist(motorRunningLog);
      testEntityManager.persist(motorRunningLog2);
      testEntityManager.persist(motorRunningLog3);

      List<MotorRunningRateDto> rateDtoList = motorRunningLogRepository.readMotorRunningRate(
          localDateTime.plusHours(3), Duration.DAY);

      assertThat(rateDtoList.size()).isEqualTo(3);

    }

    @Test
    void 최근_한달_동안_모터_가동률_조회() {
      LocalDateTime localDateTime = LocalDateTime.of(2024, 5, 20, 0, 0, 0);
      MotorRunningLog motorRunningLog2 = MotorRunningLog.builder()
          .createdAt(localDateTime.plusDays(1)).on(false).build();
      MotorRunningLog motorRunningLog3 = MotorRunningLog.builder()
          .createdAt(localDateTime.plusDays(2)).on(true).build();
      testEntityManager.persist(motorRunningLog2);
      testEntityManager.persist(motorRunningLog3);

      List<MotorRunningRateDto> rateDtoList = motorRunningLogRepository.readMotorRunningRate(
          localDateTime.plusDays(30), Duration.MONTH);
      System.out.println(rateDtoList.get(0).getTimestamp());
      assertThat(rateDtoList.size()).isEqualTo(2);
    }

    @Test
    void 최근_일주일간_모터_가동기록이_없을_때_가동률_조회() {
      LocalDateTime localDateTime = LocalDateTime.of(2024, 5, 20, 0, 0, 0);
      List<MotorRunningRateDto> rateDtoList = motorRunningLogRepository.readMotorRunningRate(
          localDateTime.plusDays(7), Duration.WEEK);
      assertThat(rateDtoList.size()).isZero();
    }
  }

  @Nested
  class 개별_모터_가동률_조회 {

    @Test
    void 최근_일주일_동안_모터_가동률_조회() {
      Motor motor = TestFixtureFactory.createMotor();
      testEntityManager.persist(motor);
      LocalDateTime localDateTime = LocalDateTime.of(2024, 5, 20, 0, 0, 0);
      MotorRunningLog motorRunningLog = MotorRunningLog.builder()
          .createdAt(localDateTime).on(true)
          .motor(motor)
          .build();
      MotorRunningLog motorRunningLog2 = MotorRunningLog.builder()
          .motor(motor)
          .createdAt(localDateTime.plusDays(1)).on(false).build();
      MotorRunningLog motorRunningLog3 = MotorRunningLog.builder()
          .motor(motor)
          .createdAt(localDateTime.plusDays(2)).on(true).build();
      testEntityManager.persist(motorRunningLog);
      testEntityManager.persist(motorRunningLog2);
      testEntityManager.persist(motorRunningLog3);

      List<MotorRunningRateDto> rateDtoList = motorRunningLogRepository.readMotorRunningRateById(1L,
          localDateTime.plusDays(3), Duration.WEEK);

      assertThat(rateDtoList.size()).isEqualTo(3);

      assertThat(rateDtoList.get(0).getRate()).isEqualTo(100.0);
      assertThat(rateDtoList.get(1).getRate()).isEqualTo(0.0);
      assertThat(rateDtoList.get(2).getRate()).isEqualTo(100.0);
    }


  }

}