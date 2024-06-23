package live.aiotone.monitoring.repository;

import live.aiotone.monitoring.domain.MotorRunningLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MotorRunningLog Repository.
 */
public interface MotorRunningLogRepository extends JpaRepository<MotorRunningLog, Long>,
    MotorRunningLogRepositoryCustom {

}
