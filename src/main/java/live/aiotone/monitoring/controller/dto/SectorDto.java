package live.aiotone.monitoring.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class SectorDto {

  private final Long sectorId;
  private final String sectorName;

}