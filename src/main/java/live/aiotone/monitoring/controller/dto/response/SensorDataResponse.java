package live.aiotone.monitoring.controller.dto.response;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * 센서 데이터 응답 Dto.
 */
@Getter
@Builder
public class SensorDataResponse {

  private List<SensorDataDto> sensorDataDtoList;

  /**
   * SensorDataResponse 의 SensorDataDto 를 정의한 Dto.
   */
  @Getter
  @Builder
  public static class SensorDataDto {

    private Instant time;
    private double value;

  }
}
