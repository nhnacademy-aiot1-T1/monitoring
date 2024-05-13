package live.aiotone.monitoring.repository;

import live.aiotone.monitoring.domain.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector, Long> {

}
