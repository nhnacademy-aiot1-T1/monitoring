package live.aiotone.monitoring.service;

import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import live.aiotone.monitoring.controller.dto.response.MotorDetailResponse;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;

/**
 * 모터 Service.
 */
public interface MotorService {

  /**
   * 모터 목록 조회.
   *
   * @return 모터 리스트.
   */
  List<Motor> readMotorList();

  /**
   * 모터 가동률 조회.
   *
   * @param duration 조회 기간.
   * @return 모터 가동률.
   */
  List<MotorRunningRateDto> readMotorRunningRate(Duration duration);

  /**
   * 개별 모터 가동률 조회.
   *
   * @param motorId  가동률을 조회할 모터 아이디
   * @param duration 조회 기간
   * @return 모터 가동률.
   */
  List<MotorRunningRateDto> readMotorRunningRateById(Long motorId, Duration duration);


  /**
   * 모터의 섹터 수정.
   *
   * @param motorId 모터 아이디
   * @param sectorId 섹터 아이디
   */
  void updateMotorSector(Long motorId, Long sectorId);

  MotorDetailResponse getMotorDetail(Long motorId);
}
