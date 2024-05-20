package live.aiotone.monitoring.service;

import java.util.List;
import live.aiotone.monitoring.domain.Motor;

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

}
