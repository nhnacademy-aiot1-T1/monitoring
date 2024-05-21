package live.aiotone.monitoring.repository;

import java.util.List;
import live.aiotone.monitoring.domain.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Sector Entity Repository.
*/
public interface SectorRepository extends JpaRepository<Sector, Long> {

  @Query("SELECT s FROM Sector s JOIN FETCH s.motors")
  List<Sector> findAllWithMotors();

}
