package live.aiotone.monitoring.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 잘못된 요청 예외.
 */
public class BadRequestException extends BusinessException {

  /**
   * 예외 메시지를 받아서 BusinessException 을 생성.
   *
   * @param message 예외 메시지
   */
  protected BadRequestException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}
