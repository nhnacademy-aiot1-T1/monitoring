package live.aiotone.monitoring.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import live.aiotone.monitoring.base.ServiceTestBase;
import live.aiotone.monitoring.common.exception.MotorNotFoundException;
import live.aiotone.monitoring.common.holder.ClockHolder;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import live.aiotone.monitoring.controller.dto.response.MotorDetailResponse;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.domain.Sensor;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import live.aiotone.monitoring.repository.MotorRepository;
import live.aiotone.monitoring.repository.MotorRunningLogRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

@SuppressWarnings("ALL")
class MotorServiceImplTest extends ServiceTestBase {

  @InjectMocks
  MotorServiceImpl motorService;
  @Mock
  MotorRepository motorRepository;
  @Mock
  MotorRunningLogRepository motorRunningLogRepository;

  @Spy
  ClockHolder clockHolder = new ClockHolder() {
    @Override
    public Clock getClock() {
      return Clock.fixed(Instant.parse("2000-01-01T00:00:00Z"), ZoneOffset.UTC);
    }
  };

  @Nested
  class Motor_조회 {

    @Test
    void motorRepository에서_모든_motor를_조회해서_반환() {
      // given
      when(motorRepository.findAll()).thenReturn(List.of());
      // when
      List<Motor> motors = motorService.readMotorList();
      // then
      assertThat(motors).isNotNull();
      verify(motorRepository, times(1)).findAll();
    }
  }

  @Nested
  class Motor_상세정보_조회 {


    @Test
    void getMotorDetail_테스트() {
      // given
      Long motorId = 1L;
      Sector sector = TestFixtureFactory.createSector();
      Sensor sensor1 = TestFixtureFactory.createSensor();
      Sensor sensor2 = TestFixtureFactory.createSensor();
      List<Sensor> sensors = List.of(sensor1, sensor2);
      Motor motor = Motor.builder()
          .id(motorId)
          .sector(sector)
          .deviceName("deviceName")
          .motorName("motorName")
          .on(true)
          .normal(true)
          .sensors(sensors)
          .build();
      when(motorRepository.findById(motorId)).thenReturn(Optional.of(motor));

      // when
      MotorDetailResponse motorDetail = motorService.getMotorDetail(motorId);

      // then
      assertThat(motorId).isEqualTo(motorDetail.getMotorId());
      assertThat(sensors.size()).isEqualTo(motorDetail.getSensors().size());
    }

    @Test
    void 모터Id에_해당하는_모터가_없을_경우_MotorNotFoundException_발생() {
      // given
      Long motorId = 1L;
      when(motorRepository.findById(motorId)).thenReturn(Optional.empty());
      // when, then
      assertThatThrownBy(() -> motorService.getMotorDetail(motorId))
          .isInstanceOf(MotorNotFoundException.class);
    }

  }

  @Nested
  class MotorRunningRate_조회 {

    @Test
    void 모터Id에_해당하는_모터의_모터_가동률을_조회할_때_MotorRunningRateDto를_반환() {
      // given
      Long motorId = 1L;
      when(motorRunningLogRepository.readMotorRunningRateById(anyLong(), any(), any()))
          .thenReturn(List.of(new MotorRunningRateDto("2021-01-01", 0.1)));
      // when
      List<MotorRunningRateDto> runningRateDto = motorService.readMotorRunningRateById(motorId, Duration.DAY);
      // then
      assertThat(runningRateDto).isNotNull();
      verify(motorRunningLogRepository, times(1)).readMotorRunningRateById(anyLong(), any(), any());
    }

    @Test
    void 모든_모터의_가동률을_조회() {
      // given
      when(motorRunningLogRepository.readMotorRunningRate(any(), any()))
          .thenReturn(List.of(new MotorRunningRateDto("2021-01-01", 0.1)));
      // when, then
      List<MotorRunningRateDto> runningRateDto = motorService.readMotorRunningRate(Duration.DAY);

      assertThat(runningRateDto).isNotNull();
      verify(motorRunningLogRepository, times(1)).readMotorRunningRate(any(), any());
    }

  }

}