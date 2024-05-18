package live.aiotone.monitoring.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Sector DTO.
 */

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SectorDto {

  private final Long sectorId;
  private final String sectorName;

}
