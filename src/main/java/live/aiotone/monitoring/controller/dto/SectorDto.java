package live.aiotone.monitoring.controller.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Sector DTO.
 */

@Getter
@Builder
public class SectorDto {

  private final Long sectorId;
  private final String sectorName;

}
