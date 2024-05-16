package live.aiotone.monitoring.common.advice;

import com.nhnacademy.common.dto.CommonResponse;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import live.aiotone.monitoring.common.exception.BusinessException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 공통 예외 처리 클래스.
 */
@RestControllerAdvice
public class CommonRestControllerAdvice {

  /**
   * 비즈니스 로직에서 발생하는 예외 처리.
   *
   * @param e        비즈니스 로직에서 발생하는 예외.
   * @param response HttpServletResponse
   * @return 공통 응답 객체에 예외 메시지를 담아 반환.
   */
  @ExceptionHandler(BusinessException.class)
  public CommonResponse<String> handleBusinessException(final BusinessException e,
      final HttpServletResponse response) {
    int httpStatus = e.getHttpStatus().value();
    String message = e.getMessage();
    response.setStatus(httpStatus);
    return CommonResponse.fail(message);
  }

  /**
   * 컨트롤러에서 입력값 유효성 검증 실패 예외 처리.
   *
   * @param e handleMethodArgumentNotValidException.
   * @return 공통 응답 객체에 유효하지 않은 필드들의 메시지를 담아 반환.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public CommonResponse<String> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException e) {
    return CommonResponse.fail(e.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(",")));
  }

  /**
   * 서버 내부에서 발생한 예외 중 핸들링되지 않은 예외 처리.
   *
   * @param e 핸들링되지 않은 예외.
   * @return 공통 응답 객체에 예외 메시지를 담아 반환.
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public CommonResponse<String> handleException(final Exception e) {
    return CommonResponse.fail(e.getMessage());
  }

}
