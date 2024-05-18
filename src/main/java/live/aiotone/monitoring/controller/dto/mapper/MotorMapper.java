package live.aiotone.monitoring.controller.dto.mapper;

import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorDto;
import live.aiotone.monitoring.domain.Motor;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Motor 엔티티관련 Dto 매핑을 담당하는 Mapper 인터페이스.
 */
@Mapper(componentModel = "spring")
public interface MotorMapper {

  @Named("toDto")
  @Mapping(source = "id", target = "motorId")
  @Mapping(source = "motorName", target = "motorName")
  @Mapping(source = "sector.id", target = "sectorId")
  @Mapping(source = "sector.sectorName", target = "sectorName")
  @Mapping(source = "on", target = "on")
  @Mapping(source = "normal", target = "normal")
  MotorDto toDto(Motor motor);

  @IterableMapping(qualifiedByName = "toDto")
  List<MotorDto> toDtoList(List<Motor> motors);


}
