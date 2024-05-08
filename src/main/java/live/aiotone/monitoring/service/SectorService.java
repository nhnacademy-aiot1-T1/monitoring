package live.aiotone.monitoring.service;

import java.util.List;
import live.aiotone.monitoring.repository.entity.Sector;

public interface SectorService {

  List<Sector> readSectorList();

}
