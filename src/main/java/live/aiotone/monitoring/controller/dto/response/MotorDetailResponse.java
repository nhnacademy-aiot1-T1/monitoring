package live.aiotone.monitoring.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * MotorDetail 응답 DTO 클래스.
 */
@Getter
@Builder
public class MotorDetailResponse {

  private final Long motorId;
  private final String motorName;
  private final Long sectorId;
  private final String sectorName;
  @JsonProperty("isOn")
  private final boolean on;
  private final String status;
  private final List<MotorDetailResponse.Sensor> sensors;

  /**
   * MotorDetail 응답 DTO 의 Sensor 정보 클래스.
   */
  @Getter
  @Builder
  public static class Sensor {

    private final Long sensorId;
    private final String sensorType;
  }
}