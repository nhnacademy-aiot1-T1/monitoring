package live.aiotone.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import live.aiotone.monitoring.domain.Sector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("ALL")
public class SectorRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

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
            Sector sector = new Sector(null, "sector1");
            Sector expectedSector = new Sector(1L, "sector1");

            // when
            Sector savedSector = sectorRepository.save(sector);

            // then
            assertThat(savedSector).usingRecursiveComparison().isEqualTo(expectedSector);
        }
    }
}


