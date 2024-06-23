package live.aiotone.monitoring.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 모터 개요 응답 DTO.
 */
@Getter
@Builder
public class MotorOverviewResponse {

  private final int totalMotorCount;
  private final int totalOnMotorCount;
  private final int totalNormalMotorCount;
}
