package live.aiotone.monitoring.controller.dto.request;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/**
* Sector 생성 요청 DTO.
*/
@Getter
public class CreateSectorRequest {

  private final String sectorName;

  @JsonCreator
  public CreateSectorRequest(String sectorName) {
    this.sectorName = sectorName;
  }
}
