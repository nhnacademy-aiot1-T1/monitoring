package live.aiotone.monitoring.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import live.aiotone.monitoring.common.exception.sector.SectorNotFoundException;
import live.aiotone.monitoring.controller.dto.mapper.SectorMapperImpl;
import live.aiotone.monitoring.controller.dto.request.CreateSectorRequest;
import live.aiotone.monitoring.controller.dto.request.UpdateSectorNameRequest;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import live.aiotone.monitoring.service.SectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

@WebMvcTest(SectorController.class)
@Import({SectorMapperImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SectorControllerTest {

  private final String path = "/api/monitor/sectors";
  @MockBean
  SectorService sectorService;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  public void sectorSetup() {
    Sector sector1 = Sector.builder().id(1L).sectorName("sector1").build();
    Sector sector2 = Sector.builder().id(2L).sectorName("sector2").build();
    when(sectorService.readSectorList()).thenReturn(
        List.of(sector1, sector2));
  }

  @Nested
  class Sector_조회 {


    @Test
    void sectorList를_조회한다() throws Exception {
      mockMvc.perform(get(path))
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


    @Test
    void sector를_생성한다() throws Exception {
      CreateSectorRequest createSectorRequest = new CreateSectorRequest("sector1");

      when(sectorService.createSector(any(String.class))).thenReturn(
          Sector.builder().id(1L).sectorName("sector1").build());

      mockMvc.perform(post(path)
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
      mockMvc.perform(post(path)
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

    Stream<String> sectorIdProvider() {
      return Stream.of("1", "2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    void sector_삭제에_성공하면_상태코드_200을_반환한다(String sectorId) throws Exception {
      URI deleteUri = UriComponentsBuilder.fromUriString(path)
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
      URI deleteUri = UriComponentsBuilder.fromUriString(path)
          .pathSegment(sectorId)
          .encode()
          .build()
          .toUri();

      doThrow(new SectorNotFoundException(Long.parseLong(sectorId))).when(sectorService)
          .deleteSectorById(anyLong());

      mockMvc.perform(delete(deleteUri))
          .andDo(print())
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("status").value("fail"));
    }
  }

  @Nested
  class Sector_수정 {

    @Test
    void sector를_수정한다() throws Exception {
      URI updateUri = UriComponentsBuilder.fromUriString(path)
          .pathSegment("{sectorId}")
          .build(1);

      UpdateSectorNameRequest updateSectorNameRequest = new UpdateSectorNameRequest("sector1");
      Sector sector = Sector.builder().id(1L).sectorName("sector1").build();
      when(sectorService.updateSectorName(anyLong(), any(String.class))).thenReturn(sector);

      mockMvc.perform(put(updateUri)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  objectMapper.writeValueAsString(updateSectorNameRequest))
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
      URI updateUri = UriComponentsBuilder.fromUriString(path)
          .pathSegment("{sectorId}")
          .build(sectorId);

      doThrow(new SectorNotFoundException(sectorId)).when(sectorService)
          .updateSectorName(anyLong(), any(String.class));

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

  @Nested
  class Sector_개요 {

    @Test
    void sector_개요를_조회한다() throws Exception {
      when(sectorService.readSectorOverviewList()).thenReturn(
          List.of(TestFixtureFactory.createSectorOverView(),
              TestFixtureFactory.createSectorOverView())
      );

      mockMvc.perform(get(path + "/overview"))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"))
          .andExpect(jsonPath("data.sectors").isArray());
    }
  }
}