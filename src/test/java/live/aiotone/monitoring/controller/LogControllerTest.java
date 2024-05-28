package live.aiotone.monitoring.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.controller.dto.response.ErrorLogResponse;
import live.aiotone.monitoring.service.LogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LogController.class)
class LogControllerTest {

  @Autowired
  LogController logController;

  @MockBean
  LogService logService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void readErrorLogs() throws Exception {
    // given
    ErrorLogResponse errorLogResponse = ErrorLogResponse.builder()
        .total(10)
        .page(1)
        .size(10)
        .logs(List.of(
            ErrorLogResponse.ErrorLog.builder()
                .motorId(1L)
                .motorName("Motor1")
                .sensorType("Temperature")
                .score(85.5)
                .time(LocalDateTime.now())
                .build()
        ))
        .build();

    Mockito.when(logService.readErrorLogs(anyDouble(), any(Pageable.class))).thenReturn(errorLogResponse);

    // when
    mockMvc.perform(get("/api/monitor/logs/error"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.total").value(10))
        .andExpect(jsonPath("$.data.page").value(1))
        .andExpect(jsonPath("$.data.size").value(10))
        .andExpect(jsonPath("$.data.logs[0].motorId").value(1L))
        .andExpect(jsonPath("$.data.logs[0].motorName").value("Motor1"))
        .andExpect(jsonPath("$.data.logs[0].sensorType").value("Temperature"))
        .andExpect(jsonPath("$.data.logs[0].score").value(85.5));
  }
}