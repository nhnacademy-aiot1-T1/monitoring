package live.aiotone.monitoring.service;


import live.aiotone.monitoring.domain.MotorOverview;

/**
 * 공장 내 종합 정보 조회 Service.
 */
public interface OverviewService {

  /**
   * 공장 내 모터 종합 정보 조회.
   *
   * @return FactoryOverview;
   */
  MotorOverview readMotorOverview();


}
