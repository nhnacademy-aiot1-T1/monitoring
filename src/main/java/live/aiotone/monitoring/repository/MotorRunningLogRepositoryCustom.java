package live.aiotone.monitoring.repository;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * MotorRunningLog Querydsl 로 동적 쿼리를 만들기 위한 RepositoryCustom.
 */
@NoRepositoryBean
public interface MotorRunningLogRepositoryCustom {

  List<MotorRunningRateDto> readMotorRunningRate(LocalDateTime currentTime, Duration duration);

  List<MotorRunningRateDto> readMotorRunningRateById(Long motorId, LocalDateTime currentTime,
      Duration duration);
}
