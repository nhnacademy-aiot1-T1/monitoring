package live.aiotone.monitoring.controller.dto.response;

import java.util.List;
import live.aiotone.monitoring.controller.dto.SectorDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Sector 목록 조회 응답 DTO.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadSectorListResponse {

  private final  List<SectorDto>  sectors;

  public static ReadSectorListResponse of(List<SectorDto> sectors) {
    return new ReadSectorListResponse(sectors);
  }

}
