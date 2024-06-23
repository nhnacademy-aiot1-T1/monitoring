package live.aiotone.monitoring.controller.dto.mapper;

import java.util.List;
import live.aiotone.monitoring.controller.dto.SectorDto;
import live.aiotone.monitoring.domain.Sector;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
  @Named("toDto")
  @Mapping(source = "id", target = "sectorId")
  @Mapping(source = "sectorName", target = "sectorName")
  SectorDto toDto(Sector sector);

  /**
   * Sector 엔티티 도메인 리스트를 SectorDto 리스트로 변환.
   *
   * @param sectors Sector 도메인 리스트
   * @return SectorDto 목록
   */
  @IterableMapping(qualifiedByName = "toDto")
  List<SectorDto> toDtoList(List<Sector> sectors);

}
