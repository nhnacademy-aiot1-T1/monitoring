package live.aiotone.monitoring.controller;

import com.nhnacademy.common.dto.CommonResponse;
import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorDto;
import live.aiotone.monitoring.controller.dto.mapper.MotorMapper;
import live.aiotone.monitoring.controller.dto.mapper.SensorScoreMapper;
import live.aiotone.monitoring.controller.dto.response.MotorDetailResponse;
import live.aiotone.monitoring.controller.dto.response.MotorListResponse;
import live.aiotone.monitoring.controller.dto.response.MotorRunningRateResponse;
import live.aiotone.monitoring.controller.dto.response.SensorDataResponse;
import live.aiotone.monitoring.controller.dto.response.SensorScoresResponse;
import live.aiotone.monitoring.controller.dto.response.SensorScoresResponse.SensorScoreDto;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;
import live.aiotone.monitoring.domain.SensorData;
import live.aiotone.monitoring.domain.SensorScore;
import live.aiotone.monitoring.service.MotorService;
import live.aiotone.monitoring.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 모터 관련 API 컨트롤러.
 */
@RestController
@RequestMapping("api/monitor/motors")
@RequiredArgsConstructor
public class MotorController {

  private static final String LOCAL_DATE_TIME_NOW_EL = "#{T(java.time.LocalDateTime).now()}";

  private final MotorService motorService;
  private final SensorService sensorService;
  private final MotorMapper motorMapper;
  private final SensorScoreMapper sensorScoreMapper;

  /**
   * 모터 리스트 조회요청 핸들러.
   *
   * @return 성공 시 모터 리스트 반환.
   */
  @GetMapping
  public CommonResponse<MotorListResponse> readMotorList() {
    List<MotorDto> motorDtoList = motorMapper.toDtoList(motorService.readMotorList());
    MotorListResponse data = MotorListResponse.of(motorDtoList);
    return CommonResponse.success(data);
  }

  /**
   * 총 모터 가동률 조회 요청 핸들러.
   *
   * @param duration 조회 기간
   * @return 모터 가동률 반환.
   */
  @GetMapping("/running-rate")
  public CommonResponse<MotorRunningRateResponse> readMotorRunningRate(
      @RequestParam(defaultValue = "DAY") Duration duration) {
    MotorRunningRateResponse data = MotorRunningRateResponse.of(
        motorService.readMotorRunningRate(duration));
    return CommonResponse.success(data);
  }

  /**
   * 개별 모터 가동률 조회 요청 핸들러.
   *
   * @param motorId  가동률을 조회할 모터 아이디
   * @param duration 조회 기간
   * @return 모터 가동률
   */
  @GetMapping("/{motorId}/running-rate")
  public CommonResponse<MotorRunningRateResponse> readMotorRunningRate(
      @PathVariable Long motorId,
      @RequestParam(defaultValue = "DAY") Duration duration) {
    MotorRunningRateResponse data = MotorRunningRateResponse.of(
        motorService.readMotorRunningRateById(motorId, duration));
    return CommonResponse.success(data);
  }

  /**
   * 센서 점수 조회 요청 핸들러.
   *
   * @param motorId  모터 아이디
   * @param sensorId 센서 아이디
   * @param start    조회 시작일
   * @param end      조회 종료일
   * @return 센서 점수 조회 응답
   */
  @GetMapping("/{motorId}/sensors/{sensorId}/scores")
  public CommonResponse<SensorScoresResponse> readSensorScores(@PathVariable Long motorId,
      @PathVariable Long sensorId,
      @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now().minusHours(1)}") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
      @RequestParam(required = false, defaultValue = LOCAL_DATE_TIME_NOW_EL) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
    List<SensorScore> sensorScoreList = sensorService.readSensorScores(motorId, sensorId, start, end);
    List<SensorScoreDto> sesorScoreDtoList = sensorScoreMapper.toDtoList(sensorScoreList);
    return CommonResponse.success(SensorScoresResponse.builder()
        .startDate(start)
        .endDate(end)
        .sensorId(sensorId)
        .scores(sesorScoreDtoList)
        .build());
  }

  /**
   * 센서 데이터 조회 요청 핸들러.
   *
   * @param motorId  모터 아이디
   * @param sensorId 센서 아이디
   * @param duration 조회 기간
   * @return 센서 데이터 조회 응답
   */
  @GetMapping("/{motorId}/sensors/{sensorId}/data")
  public CommonResponse<SensorDataResponse> readSensorData(@PathVariable Long motorId,
      @PathVariable Long sensorId,
      @RequestParam(required = false, defaultValue = "DAY") SensorData.Duration duration) {
    List<SensorData> sensorDataDtoList = sensorService.readSensorData(motorId, sensorId, duration);
    return CommonResponse.success(SensorDataResponse.from(sensorDataDtoList));
  }


  /**
   * 모터 상세 조회 요청 핸들러.
   *
   * @param motorId 모터 아이디
   * @return 모터 상세 조회 응답
   */
  @GetMapping("/{motorId}")
  public CommonResponse<MotorDetailResponse> readMotorDetail(@PathVariable Long motorId) {
    MotorDetailResponse motorDetail = motorService.getMotorDetail(motorId);
    return CommonResponse.success(motorDetail);
  }

  @PutMapping("/{motorId}/sectors/{sectorId}")
  public CommonResponse<Void> updateMotorSector(@PathVariable Long motorId, @PathVariable Long sectorId) {
    motorService.updateMotorSector(motorId, sectorId);
    return CommonResponse.success(null);
  }
}
