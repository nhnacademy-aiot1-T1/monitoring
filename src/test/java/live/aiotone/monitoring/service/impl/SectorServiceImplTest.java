package live.aiotone.monitoring.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import live.aiotone.monitoring.base.ServiceTestBase;
import live.aiotone.monitoring.common.exception.sector.SectorNotFoundException;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.repository.SectorRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class SectorServiceImplTest extends ServiceTestBase {

  @InjectMocks
  private SectorServiceImpl sectorService;

  @Mock
  private SectorRepository sectorRepository;

  @Nested
  class Sector_조회 {

    @Test
    void sectorRepository에서_모든_sector를_조회해서_반환() {
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
  class Sector_생성 {

    @Test
    void sectorRepository에_sectorName으로_새로운_sector를_저장() {
      // given
      String newSectorName = "sector1";
      // when
      sectorService.createSector(newSectorName);
      // then
      verify(sectorRepository, times(1)).save(any(Sector.class));
    }
  }

  @Nested
  class Sector_삭제 {

    @Test
    void sectorRepository에서_sectorId에_해당하는_sector를_삭제() {
      // given
      Long sectorId = 1L;
      when(sectorRepository.existsById(sectorId)).thenReturn(true);
      // when
      sectorService.deleteSectorById(sectorId);
      // then
      verify(sectorRepository, times(1)).deleteById(sectorId);
    }

    @Test
    void sectorRepository에_존재하지_않는_sectorId를_삭제하려고_할_때_SectorNotFoundException_발생() {
      // given
      Long sectorId = 1L;
      when(sectorRepository.existsById(sectorId)).thenReturn(false);
      // when
      // then
      assertThatThrownBy(() -> sectorService.deleteSectorById(sectorId))
          .isInstanceOf(SectorNotFoundException.class);
    }
  }
}