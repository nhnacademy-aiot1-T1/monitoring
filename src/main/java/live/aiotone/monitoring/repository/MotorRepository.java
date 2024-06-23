package live.aiotone.monitoring.repository;

import live.aiotone.monitoring.domain.Motor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Motor Entity Repository.
 */
public interface MotorRepository extends JpaRepository<Motor, Long> {

}
