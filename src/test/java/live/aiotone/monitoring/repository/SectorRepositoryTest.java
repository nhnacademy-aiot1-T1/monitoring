package live.aiotone.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import live.aiotone.monitoring.base.RepositoryTestBase;
import live.aiotone.monitoring.domain.Sector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class SectorRepositoryTest extends RepositoryTestBase {


    @Autowired
    private SectorRepository sectorRepository;

    @BeforeEach
    void setup(){
        sectorRepository.deleteAll();
        testEntityManager.getEntityManager().createNativeQuery("ALTER TABLE sector AUTO_INCREMENT = 1").executeUpdate();
    }

    @Nested
    class Sector_save_테스트{
        @Test
        void sector_save(){
            // given
            Sector sector = Sector.builder()
                .sectorName("sector1")
                .build();

            Sector expectedSector = Sector.builder().id(1L)
                .sectorName("sector1")
                .build();

            // when
            Sector savedSector = sectorRepository.save(sector);

            // then
            assertThat(savedSector).usingRecursiveComparison().isEqualTo(expectedSector);
        }
    }
}


