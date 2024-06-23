package live.aiotone.monitoring.repository;

import live.aiotone.monitoring.domain.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Sensor Repository.
 */
public interface SensorRepository extends JpaRepository<Sensor, Long> {

}
