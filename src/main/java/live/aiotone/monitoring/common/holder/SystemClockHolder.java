package live.aiotone.monitoring.common.holder;

import java.time.Clock;
import org.springframework.stereotype.Component;

/**
 * ClockHolder 를 구현한 클래스 현재 시스템 시간을 반환.
 */
@Component
public class SystemClockHolder implements ClockHolder {

  @Override
  public Clock getClock() {
    return Clock.systemDefaultZone();
  }
}
