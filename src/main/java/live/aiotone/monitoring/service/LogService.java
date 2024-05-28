package live.aiotone.monitoring.service;

import live.aiotone.monitoring.controller.dto.response.ErrorLogResponse;
import org.springframework.data.domain.Pageable;

/**
 * 로그 서비스.
 */
public interface LogService {

  /**
   * 에러 로그 조회.
   *
   * @param pageable 페이지 정보
   * @return 에러 로그 응답
   */
  ErrorLogResponse readErrorLogs(double score, Pageable pageable);

}
