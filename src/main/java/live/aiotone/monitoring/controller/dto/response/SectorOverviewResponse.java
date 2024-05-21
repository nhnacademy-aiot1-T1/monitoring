package live.aiotone.monitoring.controller.dto.response;

import java.util.List;
import live.aiotone.monitoring.domain.SectorOverView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Sector 개요 응답 DTO.
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
public class SectorOverviewResponse {

  private final List<SectorOverView> sectors;

}
