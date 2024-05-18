package live.aiotone.monitoring.controller.dto.response;

import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모터 리스트 조회 응답 DTO.
 */
@AllArgsConstructor(staticName = "of")
@Getter
public class ReadMotorListResponse {

  List<MotorDto> motors;
}
