package live.aiotone.monitoring.controller;

import com.nhnacademy.common.dto.CommonResponse;
import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorDto;
import live.aiotone.monitoring.controller.dto.mapper.MotorMapper;
import live.aiotone.monitoring.controller.dto.response.ReadMotorListResponse;
import live.aiotone.monitoring.service.MotorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 모터 관련 API 컨트롤러.
 */
@RestController
@RequestMapping("api/monitor/motors")
@RequiredArgsConstructor
public class MotorController {

  private final MotorService motorService;
  private final MotorMapper motorMapper;

  /**
   * 모터 리스트 조회요청 핸들러.
   *
   * @return 성공 시 모터 리스트 반환.
   */
  @GetMapping
  public CommonResponse<ReadMotorListResponse> readMotorList() {
    List<MotorDto> motorDtoList = motorMapper.toDtoList(motorService.readMotorList());
    ReadMotorListResponse data = ReadMotorListResponse.of(motorDtoList);
    return CommonResponse.success(data);
  }

}
