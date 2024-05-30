package live.aiotone.monitoring.controller.dto.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import live.aiotone.monitoring.domain.SensorData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 센서 데이터 응답 Dto.
 */
@Getter
@Builder
public class SensorDataResponse {

  private final List<SensorDataDto> sensorData;

  public static SensorDataResponse from(List<SensorData> sensorDataList) {
    return SensorDataResponse.builder()
        .sensorData(sensorDataList.stream().map(SensorDataDto::from).collect(Collectors.toList()))
        .build();
  }

  /**
   * SensorDataResponse 의 SensorDataDto 를 정의한 Dto.
   */
  @Getter
  @NoArgsConstructor(access = AccessLevel.PUBLIC) // influxdb 라이브러리에서 사용하기 위해 public 으로 변경
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @Builder
  public static class SensorDataDto {

    private LocalDateTime time;
    private double value;

    static SensorDataDto from(SensorData sensorData) {

      return SensorDataDto.builder()
          .time(LocalDateTime.ofInstant(sensorData.getTime(), ZoneOffset.UTC))
          .value(sensorData.getValue())
          .build();
    }
  }
}
