package live.aiotone.monitoring.controller.dto.mapper;

import java.util.List;
import live.aiotone.monitoring.controller.dto.response.SensorScoresResponse.SensorScoreDto;
import live.aiotone.monitoring.domain.SensorScore;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * SensorScore 엔티티관련 Dto 매핑을 담당하는 Mapper 인터페이스.
 */
@Mapper(componentModel = "spring")
public interface SensorScoreMapper {

  @Mapping(target = "time", source = "time")
  @Mapping(target = "score", source = "score")
  @Named("toDto")
  SensorScoreDto toDto(SensorScore sensorScore);

  @IterableMapping(qualifiedByName = "toDto")
  List<SensorScoreDto> toDtoList(List<SensorScore> sensorScoreDtoList);

}
