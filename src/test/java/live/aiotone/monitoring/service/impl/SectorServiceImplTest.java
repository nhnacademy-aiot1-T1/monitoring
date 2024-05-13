package live.aiotone.monitoring.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import live.aiotone.monitoring.base.ServiceTest;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.repository.SectorRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
@SuppressWarnings("all")
class SectorServiceImplTest extends ServiceTest {

  @InjectMocks
  private SectorServiceImpl sectorService;

  @Mock
  private SectorRepository sectorRepository;

  @Nested
  class Sector_조회{
    @Test
    void sectorRepository에서_모든_sector를_조회해서_반환 () {
      // given
      when(sectorRepository.findAll()).thenReturn(List.of());
      // when
      List<Sector> sectors = sectorService.readSectorList();
      // then
      assertThat(sectors).isNotNull();
      verify(sectorRepository, times(1)).findAll();
    }
  }

  @Nested
  class Sector_생성{
    @Test
    void sectorRepository에_sectorName으로_새로운_sector를_저장 () {
      // given
      String newSectorName = "sector1";
      // when
      sectorService.createSector(newSectorName);
      // then
      verify(sectorRepository, times(1)).save(any(Sector.class));
    }
  }
}