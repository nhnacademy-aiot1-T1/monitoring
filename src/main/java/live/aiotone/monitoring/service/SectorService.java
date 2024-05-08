package live.aiotone.monitoring.service;

import java.util.List;
import live.aiotone.monitoring.domain.Sector;

public interface SectorService {

  List<Sector> readSectorList();

}
