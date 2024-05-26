package live.aiotone.monitoring.common.exception.sensor;

import live.aiotone.monitoring.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class SensorNotFoundException extends BusinessException {

  /**
   * 예외 메시지를 받아서 BusinessException을 생성.
   *
   * @param sensorId sensorId
   */
  public SensorNotFoundException(Long sensorId) {
    super("Sensor not found : " + sensorId);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
