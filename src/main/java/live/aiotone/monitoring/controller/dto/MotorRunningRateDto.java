package live.aiotone.monitoring.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

/**
 * 모터의 가동률 DTO.
 */
@Getter
public class MotorRunningRateDto {

  private final String timestamp;
  private final double rate;

  @QueryProjection
  public MotorRunningRateDto(String timestamp, double rate) {
    this.timestamp = timestamp;
    this.rate = rate;
  }
}
