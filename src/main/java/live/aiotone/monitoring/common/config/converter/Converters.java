package live.aiotone.monitoring.common.config.converter;

import live.aiotone.monitoring.domain.MotorRunningLog;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;
import live.aiotone.monitoring.domain.SensorData;
import org.springframework.core.convert.converter.Converter;

/**
 * RequestParam 으로 받은 String 값을 Enum 으로 변환하기 위한 Converter.
 */
public class Converters {

  private Converters() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * 모터 가동 로그 조회 기간 컨버터.
   */
  public static class MotorRunningLogDurationConverter implements Converter<String, MotorRunningLog.Duration> {

    @Override
    public Duration convert(String source) {
      return Duration.valueOf(source.toUpperCase());
    }
  }

  /**
   * 센서 데이터 조회 기간 컨버터.
   */
  public static class SensorDataDurationConverter implements Converter<String, SensorData.Duration> {

    @Override
    public SensorData.Duration convert(String source) {
      return SensorData.Duration.valueOf(source.toUpperCase());
    }
  }


}
