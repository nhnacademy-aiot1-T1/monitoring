package live.aiotone.monitoring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import live.aiotone.monitoring.base.IntegrationTest;
import live.aiotone.monitoring.controller.dto.request.CreateSectorRequest;
import live.aiotone.monitoring.setup.SectorSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

@SuppressWarnings("ALL")
class SectorControllerTest extends IntegrationTest {

  @Autowired
  SectorSetup sectorSetup;

  private final UriComponentsBuilder sectorUriBuilder = UriComponentsBuilder.newInstance()
      .scheme("http")
      .host("localhost")
      .port(port)
      .path("/api/monitor/sectors");

  @Nested
  class Sector_조회 {

    URI readUri = sectorUriBuilder
        .encode()
        .build()
        .toUri();

    @BeforeEach
    void setUp() {
      sectorSetup.insertSectorList();
    }

    @Test
    void sectorList를_조회한다() throws Exception {
      mockMvc.perform(get(readUri))
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              jsonPath("status").value("success"),
              jsonPath("data.sectors").isArray(),
              jsonPath("data.sectors[0].sectorId").isNumber(),
              jsonPath("data.sectors[0].sectorName").value("sector1"),
              jsonPath("data.sectors[1].sectorId").isNumber(),
              jsonPath("data.sectors[1].sectorName").value("sector2"));
    }
  }

  @Nested
  class Sector_생성 {

    URI saveUri = sectorUriBuilder
        .encode()
        .build()
        .toUri();

    @Test
    void sector를_생성한다() throws Exception {
      CreateSectorRequest createSectorRequest = new CreateSectorRequest("sector1");
      mockMvc.perform(post(saveUri)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  objectMapper.writeValueAsString(createSectorRequest))
          )
          .andDo(print())
          .andExpectAll(
              status().isOk(),
              jsonPath("status").value("success"),
              jsonPath("data.sectorId").isNumber(),
              jsonPath("data.sectorName").value("sector1"));
    }
  }

    @Nested
    class Sector_수정 {

        URI updateUri = sectorUriBuilder
            .pathSegment("{sectorId}")
            .build(1);

        @BeforeEach
        void setUp() {
          sectorSetup.insertSectorList();
        }

        @Test
        void sector를_수정한다() throws Exception {
          CreateSectorRequest createSectorRequest = new CreateSectorRequest("sector1");
          mockMvc.perform(put(updateUri)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      objectMapper.writeValueAsString(createSectorRequest))
              )
              .andDo(print())
              .andExpectAll(
                  status().isOk(),
                  jsonPath("status").value("success"),
                  jsonPath("data.sectorId").isNumber(),
                  jsonPath("data.sectorName").value("sector1"));
        }

    }
}