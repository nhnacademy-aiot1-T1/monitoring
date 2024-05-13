package live.aiotone.monitoring.controller.dto.mapper;

import live.aiotone.monitoring.controller.dto.SectorDto;
import live.aiotone.monitoring.domain.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SectorMapper {
  @Mapping(source = "id", target = "sectorId")
  @Mapping(source = "sectorName", target = "sectorName")
  SectorDto toDto(Sector sector);

}
