package live.aiotone.monitoring.setup;

import java.util.List;
import java.util.stream.Collectors;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SectorSetup {

  @Autowired
  SectorRepository sectorRepository;

  public void insertSectorList() {
    List<String> sectorNameList = List.of("sector1", "sector2", "sector3", "sector4");
    List<Sector> sectorList = sectorNameList.stream()
        .map(Sector::createByName)
        .collect(Collectors.toList());
    sectorRepository.saveAll(sectorList);
  }

}
