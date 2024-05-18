package live.aiotone.monitoring.controller.dto.request;


import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
* Sector 생성 요청 DTO.
*/
@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public final class CreateSectorRequest {

  @NotBlank(message = "sectorName은 비어있을 수 없습니다.")
  private final String sectorName;
}
