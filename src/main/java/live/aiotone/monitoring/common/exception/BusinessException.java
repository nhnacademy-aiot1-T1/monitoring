package live.aiotone.monitoring.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 비즈니스 로직에서 발생하는 예외의 최상위 클래스.
 */
public abstract class BusinessException extends RuntimeException {

  /**
   * 예외 메시지를 받아서 BusinessException을 생성.
   *
   * @param message 예외 메시지
   */
  protected BusinessException(String message) {
    super(message);
  }

  public abstract HttpStatus getHttpStatus();
}
