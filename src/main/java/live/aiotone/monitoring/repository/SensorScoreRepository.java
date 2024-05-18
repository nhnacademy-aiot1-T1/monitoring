package live.aiotone.monitoring.repository;

import live.aiotone.monitoring.domain.SensorScore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SensorScore Repository.
 */
public interface SensorScoreRepository extends JpaRepository<SensorScore, Long> {

}
