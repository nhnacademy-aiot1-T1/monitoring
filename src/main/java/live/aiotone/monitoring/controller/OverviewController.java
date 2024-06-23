package live.aiotone.monitoring.controller;

import com.nhnacademy.common.dto.CommonResponse;
import live.aiotone.monitoring.controller.dto.mapper.OverviewMapper;
import live.aiotone.monitoring.controller.dto.response.MotorOverviewResponse;
import live.aiotone.monitoring.domain.MotorOverview;
import live.aiotone.monitoring.service.OverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공장의 각 부분의 개요 요청을 처리하는 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/monitor/overview")
public class OverviewController {

  private final OverviewMapper overviewMapper;
  private final OverviewService overviewService;

  /**
   * 공장 모터 개요를 조회.
   *
   * @return 공장 모터 개요.
   */
  @GetMapping
  public CommonResponse<MotorOverviewResponse> readFactoryOverview() {
    MotorOverview motorOverview = overviewService.readMotorOverview();
    MotorOverviewResponse motorOverviewResponse = overviewMapper.toDto(motorOverview);
    return CommonResponse.success(motorOverviewResponse);
  }


}
