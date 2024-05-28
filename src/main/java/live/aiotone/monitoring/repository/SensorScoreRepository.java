package live.aiotone.monitoring.repository;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.domain.SensorScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SensorScore Repository.
 */
public interface SensorScoreRepository extends JpaRepository<SensorScore, Long> {

  List<SensorScore> findListSensorScoreBySensorIdAndTimeBetween(Long sensorId, LocalDateTime start, LocalDateTime end);

  Page<SensorScore> findAllByScoreLessThanEqualOrderByTimeDesc(double score, Pageable pageable);
}
