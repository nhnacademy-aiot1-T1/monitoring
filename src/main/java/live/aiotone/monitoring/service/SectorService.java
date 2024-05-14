package live.aiotone.monitoring.service;

import java.util.List;
import live.aiotone.monitoring.domain.Sector;

/**
 *  Sector Service.
 */
public interface SectorService {

  List<Sector> readSectorList();

  Sector createSector(String sectorName);

  Sector updateSectorName(Long sectorId, String sectorName);
}
