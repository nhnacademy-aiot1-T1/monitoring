package live.aiotone.monitoring.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import live.aiotone.monitoring.base.IntegrationTestBase;
import live.aiotone.monitoring.controller.dto.request.CreateSectorRequest;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

@SuppressWarnings("ALL")
class SectorIntegrationTest extends IntegrationTestBase {

  private final UriComponentsBuilder sectorUriBuilder = UriComponentsBuilder.newInstance()
      .scheme("http")
      .host("localhost")
      .port(port)
      .path("/api/monitor/sectors");

  @Autowired
  SectorRepository sectorRepository;

  @Nested
  class Sector_조회 {

    URI readUri = sectorUriBuilder
        .encode()
        .build()
        .toUri();

    @BeforeEach
    void setUp() {
      List<String> sectorNameList = List.of("sector1", "sector2", "sector3", "sector4");
      List<Sector> sectorList = sectorNameList.stream()
          .map(Sector::createByName)
          .collect(Collectors.toList());
      sectorRepository.saveAll(sectorList);
    }

    @Test
    void sectorList를_조회한다() throws Exception {
      mockMvc.perform(get(readUri))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"))
          .andExpect(jsonPath("data.sectors").isArray())
          .andExpect(jsonPath("data.sectors[0].sectorId").isNumber())
          .andExpect(jsonPath("data.sectors[0].sectorName").value("sector1"))
          .andExpect(jsonPath("data.sectors[1].sectorId").isNumber())
          .andExpect(jsonPath("data.sectors[1].sectorName").value("sector2"));
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
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"))
          .andExpect(jsonPath("data.sectorId").isNumber())
          .andExpect(jsonPath("data.sectorName").value("sector1"));
    }

    @Test
    void sector를_생성_시_sector_이름이_공백이면_400_상태코드를_반환() throws Exception {
      CreateSectorRequest createSectorRequest = new CreateSectorRequest("");
      mockMvc.perform(post(saveUri)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  objectMapper.writeValueAsString(createSectorRequest))
          ).andDo(print()).andExpect(status().isBadRequest())
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("status").value("fail"));
    }
  }

  @Nested
  class Sector_삭제 {
    @BeforeEach
    void setUp() {
      List<String> sectorNameList = List.of("sector1", "sector2", "sector3", "sector4");
      List<Sector> sectorList = sectorNameList.stream()
          .map(Sector::createByName)
          .collect(Collectors.toList());
      sectorRepository.saveAll(sectorList);
    }

    Stream<String> sectorIdProvider() {
      return Stream.of("1", "2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    void sector_삭제에_성공하면_상태코드_200을_반환한다(String sectorId) throws Exception {
      URI deleteUri = sectorUriBuilder
          .pathSegment(sectorId)
          .encode()
          .build()
          .toUri();

      mockMvc.perform(delete(deleteUri))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "10"})
    void 존재하지_않는_sector_삭제요청을_보내면_상태코드_404을_반환한다(String sectorId) throws Exception {
      URI deleteUri = sectorUriBuilder
          .pathSegment(sectorId)
          .encode()
          .build()
          .toUri();

      mockMvc.perform(delete(deleteUri))
          .andDo(print())
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("status").value("fail"));
    }
  }

  @Nested
  class Sector_수정 {

    @BeforeEach
    void setUp() {
      List<String> sectorNameList = List.of("sector1", "sector2", "sector3", "sector4");
      List<Sector> sectorList = sectorNameList.stream()
          .map(Sector::createByName)
          .collect(Collectors.toList());
      sectorRepository.saveAll(sectorList);
    }

    @Test
    void sector를_수정한다() throws Exception {
      URI updateUri = sectorUriBuilder
          .pathSegment("{sectorId}")
          .build(1);

      CreateSectorRequest createSectorRequest = new CreateSectorRequest("sector1");
      mockMvc.perform(put(updateUri)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  objectMapper.writeValueAsString(createSectorRequest))
          )
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"))
          .andExpect(jsonPath("data.sectorId").isNumber())
          .andExpect(jsonPath("data.sectorName").value("sector1"));
    }

    @ParameterizedTest
    @CsvSource(value = {"-1,sector1", "10,sector2"}, delimiter = ',')
    void 존재하지_않는_sector_수정요청을_보내면_상태코드_404을_반환한다(Long sectorId, String sectorName)
        throws Exception {
      CreateSectorRequest createSectorRequest = new CreateSectorRequest(sectorName);
      URI updateUri = sectorUriBuilder
          .pathSegment("{sectorId}")
          .build(sectorId);

      mockMvc.perform(put(updateUri)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  objectMapper.writeValueAsString(createSectorRequest))
          )
          .andDo(print())
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("status").value("fail"));
    }

  }
}