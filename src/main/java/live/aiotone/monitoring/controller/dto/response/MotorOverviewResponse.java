package live.aiotone.monitoring.controller.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 모터 개요 응답 DTO.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MotorOverviewResponse {

  private final int totalMotorCount;
  private final int totalOnMotorCount;
  private final int totalNormalMotorCount;
}
