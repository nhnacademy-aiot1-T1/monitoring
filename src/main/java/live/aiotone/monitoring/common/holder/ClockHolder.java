package live.aiotone.monitoring.common.holder;

import java.time.Clock;

/**
 * java.time.Clock 제공하는 Holder.
 */
public interface ClockHolder {

  /**
   * 현재 시간을 제공하는 Clock을 반환한다.
   *
   * @return Clock
   */
  Clock getClock();
}
