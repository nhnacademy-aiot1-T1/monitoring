package live.aiotone.monitoring.controller.dto.mapper;

import live.aiotone.monitoring.controller.dto.SectorDto;
import live.aiotone.monitoring.domain.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Sector 엔티티관련 Dto 매핑을 담당하는 Mapper 인터페이스.
 */
@Mapper(componentModel = "spring")
public interface SectorMapper {

  /**
   * Sector 엔티티를 SectorDto로 변환.
   *
   * @param sector Sector 엔티티
   * @return SectorDto
   */
  @Mapping(source = "id", target = "sectorId")
  @Mapping(source = "sectorName", target = "sectorName")
  SectorDto toDto(Sector sector);

}
