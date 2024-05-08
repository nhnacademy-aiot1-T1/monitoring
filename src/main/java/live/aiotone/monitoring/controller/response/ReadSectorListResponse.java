package live.aiotone.monitoring.controller.response;

import java.util.List;
import live.aiotone.monitoring.controller.dto.SectorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadSectorListResponse {

  private final  List<SectorDto>  sectors;

}
