package live.aiotone.monitoring.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 모터 DTO.
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MotorDto {

  private final Long motorId;
  private final String deviceName;
  private final String motorName;
  private final Long sectorId;
  private final String sectorName;
  @JsonProperty("isOn")
  private final boolean on;
  @JsonProperty("isNormal")
  private final boolean normal;
}
