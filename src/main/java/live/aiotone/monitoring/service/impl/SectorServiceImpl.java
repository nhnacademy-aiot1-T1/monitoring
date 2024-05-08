package live.aiotone.monitoring.service.impl;

import java.util.List;
import live.aiotone.monitoring.repository.SectorRepository;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {

  private final SectorRepository sectorRepository;

  @Override
  public List<Sector> readSectorList() {
    return sectorRepository.findAll();
  }
}
