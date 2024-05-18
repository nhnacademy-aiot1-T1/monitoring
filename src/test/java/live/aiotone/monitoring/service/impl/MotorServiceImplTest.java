package live.aiotone.monitoring.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import live.aiotone.monitoring.base.ServiceTestBase;
import live.aiotone.monitoring.domain.Motor;
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
}