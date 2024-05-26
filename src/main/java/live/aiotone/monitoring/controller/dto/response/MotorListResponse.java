package live.aiotone.monitoring.controller.dto.response;

import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 모터 리스트 조회 응답 DTO.
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
public class MotorListResponse {

  private final List<MotorDto> motors;
}
