package live.aiotone.monitoring.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Sector 이름 수정 요청 DTO.
 */
@Getter
@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
public class UpdateSectorNameRequest {

  @NotBlank(message = "sectorName은 비어있을 수 없습니다.")
  private final String sectorName;

}
