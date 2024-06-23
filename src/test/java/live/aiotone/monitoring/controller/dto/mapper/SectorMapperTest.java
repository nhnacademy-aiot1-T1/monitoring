package live.aiotone.monitoring.controller.dto.mapper;


import static org.assertj.core.api.Assertions.assertThat;

import live.aiotone.monitoring.controller.dto.SectorDto;
import live.aiotone.monitoring.domain.Sector;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class SectorMapperTest {

  private final SectorMapper sectorMapper = Mappers.getMapper(SectorMapper.class);

  @Test
  void toDto() {
    // given
    Long expectedId = 1L;
    String expectedName = "sector1";
    SectorDto expected = SectorDto.builder()
        .sectorId(expectedId)
        .sectorName(expectedName)
        .build();

    Sector sector = Sector.builder()
        .id(1L)
        .sectorName("sector1")
        .build();
    // when
    SectorDto actual = sectorMapper.toDto(sector);
    // then
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}