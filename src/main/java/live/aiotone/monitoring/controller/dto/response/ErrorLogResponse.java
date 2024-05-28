package live.aiotone.monitoring.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * 에러 로그 응답 DTO.
 */
@Builder
@Getter
public class ErrorLogResponse {

  private final List<ErrorLog> logs;

  /**
   * 에러 로그 DTO.
   */
  @Builder
  @Getter
  private static final class ErrorLog {

    private final Long motorId;
    private final String motorName;
    private final String sensorType;
    private final double score;
    private LocalDateTime time;
  }

}
