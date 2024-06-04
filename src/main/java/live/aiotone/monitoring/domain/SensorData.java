package live.aiotone.monitoring.domain;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 센서 데이터 도메인 클래스.
 */
@Getter
@NoArgsConstructor
@Measurement(name = "rawData")
public class SensorData {

  @Column(timestamp = true)
  private Instant time;

  @Column(name = "_value")
  private double value;

  @Builder
  private SensorData(Instant time, double value) {
    this.time = time;
    this.value = value;
  }

  /**
   * 센서 데이터 조회를 위한 enum 클래스.
   */
  @Getter
  public enum Duration {
    DAY("3m", 1),
    WEEK("10m", 7),
    MONTH("1d", 30);

    private final String period;
    private final int unit;

    Duration(String period, int unit) {
      this.period = period;
      this.unit = unit;
    }

    public Instant getInstant() {
      return Instant.now().minus(unit, ChronoUnit.DAYS);
    }
  }

}
