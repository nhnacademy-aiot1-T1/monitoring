package live.aiotone.monitoring.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import live.aiotone.monitoring.base.IntegrationTest;
import live.aiotone.monitoring.setup.SectorSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class SectorControllerTest extends IntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  SectorSetup sectorSetup;

  @Nested
  class Sector_조회 {
    @BeforeEach
    void setUp() {
      sectorSetup.insertSectorList();
    }
    @Test
    void sectorList를_조회한다 () throws Exception {
      mockMvc.perform(get("http://localhost:8080/api/monitor/sectors"))
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              jsonPath("status").value("success"),
              jsonPath("data.sectors", hasSize(4)),
              jsonPath("data.sectors[0].sectorId").value(1),
              jsonPath("data.sectors[0].sectorName").value("sector1"),
              jsonPath("data.sectors[1].sectorId").value(2),
              jsonPath("data.sectors[1].sectorName").value("sector2"));
    }
  }
}