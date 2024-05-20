package live.aiotone.monitoring.controller.dto.mapper;

import live.aiotone.monitoring.controller.dto.response.MotorOverviewResponse;
import live.aiotone.monitoring.domain.MotorOverview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 개요 정보를 매핑하기 위한 인터페이스.
 */
@Mapper(componentModel = "spring")
public interface OverviewMapper {

  /**
   * 개요 정보를 개요 응답 DTO로 변환.
   *
   * @param motorOverview 모터 개요 정보.
   * @return 모터 개요 응답 DTO.
   */
  @Mapping(source = "totalMotorCount", target = "totalMotorCount")
  @Mapping(source = "totalOnMotorCount", target = "totalOnMotorCount")
  @Mapping(source = "totalNormalMotorCount", target = "totalNormalMotorCount")
  MotorOverviewResponse toDto(MotorOverview motorOverview);
}
