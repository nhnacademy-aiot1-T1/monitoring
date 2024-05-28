package live.aiotone.monitoring.controller;

import com.nhnacademy.common.dto.CommonResponse;
import live.aiotone.monitoring.controller.dto.response.ErrorLogResponse;
import live.aiotone.monitoring.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitor/logs")
public class LogController {

  private final LogService logService;

  /**
   * 에러 로그 조회.
   *
   * @param pageable 페이지 정보
   * @return 에러 로그 응답
   */
  @GetMapping("/error")
  public CommonResponse<ErrorLogResponse> readErrorLogs(Pageable pageable, @RequestParam(defaultValue = "80") Double score) {
    ErrorLogResponse errorLogResponse = logService.readErrorLogs(score, pageable);
    return CommonResponse.success(errorLogResponse);
  }
}

