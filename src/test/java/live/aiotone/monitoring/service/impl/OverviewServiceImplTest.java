package live.aiotone.monitoring.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import live.aiotone.monitoring.base.ServiceTestBase;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.MotorOverview;
import live.aiotone.monitoring.service.MotorService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class OverviewServiceImplTest extends ServiceTestBase {

  @InjectMocks
  OverviewServiceImpl overviewService;
  @Mock
  MotorService motorService;

  @Nested
  class MotorOverview_조회 {

    @Test
    void readMotorOverview() {
      // given
      Motor motor1 = Motor.builder().id(1L).build();
      Motor motor2 = Motor.builder().id(2L).build();
      List<Motor> motorList = Arrays.asList(motor1, motor2);
      when(motorService.readMotorList()).thenReturn(motorList);

      // when
      MotorOverview motorOverview = overviewService.readMotorOverview();

      // then
      assertThat(motorOverview.getTotalMotorCount()).isEqualTo(motorList.size());
      verify(motorService, times(1)).readMotorList();
    }
  }
}