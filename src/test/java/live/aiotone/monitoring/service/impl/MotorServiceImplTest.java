package live.aiotone.monitoring.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import live.aiotone.monitoring.base.ServiceTestBase;
import live.aiotone.monitoring.common.exception.MotorNotFoundException;
import live.aiotone.monitoring.controller.dto.response.MotorDetailResponse;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.domain.Sensor;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import live.aiotone.monitoring.repository.MotorRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@SuppressWarnings("ALL")
class MotorServiceImplTest extends ServiceTestBase {

  @InjectMocks
  private MotorServiceImpl motorService;
  @Mock
  private MotorRepository motorRepository;

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
      assertThrows(MotorNotFoundException.class, () -> motorService.getMotorDetail(motorId));
    }

  }
}