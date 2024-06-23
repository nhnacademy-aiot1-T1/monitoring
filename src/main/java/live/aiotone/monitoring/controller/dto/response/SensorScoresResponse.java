package live.aiotone.monitoring.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * 센서 점수 조회 응답 DTO.
 */
@Getter
@Builder
public class SensorScoresResponse {

  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final Long sensorId;
  private final List<SensorScoreDto> scores;

  /**
   * 센서 점수 DTO.
   */
  @Getter
  @Builder
  public static class SensorScoreDto {

    private final LocalDateTime time;
    private final double score;
  }
}
