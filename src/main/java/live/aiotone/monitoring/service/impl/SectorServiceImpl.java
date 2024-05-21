package live.aiotone.monitoring.service.impl;

import java.util.List;
import live.aiotone.monitoring.common.exception.sector.SectorNotFoundException;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.domain.SectorOverView;
import live.aiotone.monitoring.repository.SectorRepository;
import live.aiotone.monitoring.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SectorService 구현체.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {

  private final SectorRepository sectorRepository;

  @Override
  @Transactional(readOnly = true)
  public List<Sector> readSectorList() {
    return sectorRepository.findAll();
  }

  @Override
  public Sector createSector(String sectorName) {
    Sector sector = Sector.createByName(sectorName);
    return sectorRepository.save(sector);
  }

  @Override
  public void deleteSectorById(Long sectorId) {
    if (!sectorRepository.existsById(sectorId)) {
      throw new SectorNotFoundException(sectorId);
    }
    sectorRepository.deleteById(sectorId);
  }

  @Override
  public List<SectorOverView> readSectorOverviewList() {
    List<Sector> sectors = sectorRepository.findAll();
    return SectorOverView.fromSectorList(sectors);
  }

  @Override
  public Sector updateSectorName(Long sectorId, String sectorName) {
    Sector sector = sectorRepository.findById(sectorId)
        .orElseThrow(() -> new SectorNotFoundException(sectorId));
    sector.updateName(sectorName);
    return sector;
  }
}
