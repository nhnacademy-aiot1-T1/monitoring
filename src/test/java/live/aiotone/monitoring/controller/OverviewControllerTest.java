package live.aiotone.monitoring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import live.aiotone.monitoring.controller.dto.mapper.OverviewMapperImpl;
import live.aiotone.monitoring.domain.MotorOverview;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import live.aiotone.monitoring.service.OverviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OverviewController.class)
@Import(OverviewMapperImpl.class)
@SuppressWarnings("NonAsciiCharacters")
class OverviewControllerTest {

  @Autowired
  OverviewController overviewController;

  @MockBean
  OverviewService overviewService;

  @Autowired
  MockMvc mockMvc;


  @Test
  void Overview_조회_성공_시_상태코드_200과_OverviewResponse를_응답() throws Exception {
    // given
    MotorOverview motorOverview = TestFixtureFactory.createMotorOverview();

    when(overviewService.readMotorOverview()).thenReturn(motorOverview);
    // when, then
    mockMvc.perform(get("/api/monitor/overview"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value("success"))
        .andExpect(jsonPath("data.totalMotorCount").value(motorOverview.getTotalMotorCount()))
        .andExpect(jsonPath("data.totalOnMotorCount").value(motorOverview.getTotalOnMotorCount()))
        .andExpect(jsonPath("data.totalNormalMotorCount").value(motorOverview.getTotalNormalMotorCount()));
  }
}