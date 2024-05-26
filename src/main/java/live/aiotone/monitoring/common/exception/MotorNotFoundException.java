package live.aiotone.monitoring.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 모터를 찾을 수 없을 때 발생하는 예외.
 */
public class MotorNotFoundException extends BusinessException {

  public MotorNotFoundException(Long motorId) {
    super("Motor not found. motorId: " + motorId);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
