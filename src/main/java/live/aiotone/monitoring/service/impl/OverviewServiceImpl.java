package live.aiotone.monitoring.service.impl;

import java.util.List;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.MotorOverview;
import live.aiotone.monitoring.service.MotorService;
import live.aiotone.monitoring.service.OverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Overview Service 구현체.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OverviewServiceImpl implements OverviewService {

  private final MotorService motorService;

  @Override
  public MotorOverview readMotorOverview() {
    List<Motor> motorList = motorService.readMotorList();
    return MotorOverview.createOverview(motorList);
  }
}
