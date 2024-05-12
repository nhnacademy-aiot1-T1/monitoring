package live.aiotone.monitoring.controller.dto.mapper;


import static org.assertj.core.api.Assertions.*;

import live.aiotone.monitoring.controller.dto.SectorDto;
import live.aiotone.monitoring.domain.Sector;
import org.assertj.core.api.JUnitJupiterSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    Sector sector = new Sector(1L, "sector1");
    // when
    SectorDto actual = sectorMapper.toDto(sector);
    // then
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}