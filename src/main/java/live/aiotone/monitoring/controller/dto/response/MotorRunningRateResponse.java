package live.aiotone.monitoring.controller.dto.response;

import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 모터 가동률 응답 DTO.
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
public class MotorRunningRateResponse {

  private final List<MotorRunningRateDto> rates;

}
