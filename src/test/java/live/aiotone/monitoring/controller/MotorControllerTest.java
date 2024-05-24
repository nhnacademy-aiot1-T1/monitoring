package live.aiotone.monitoring.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import live.aiotone.monitoring.controller.dto.mapper.MotorMapper;
import live.aiotone.monitoring.controller.dto.mapper.MotorMapperImpl;
import live.aiotone.monitoring.controller.dto.mapper.SectorMapper;
import live.aiotone.monitoring.controller.dto.mapper.SectorMapperImpl;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import live.aiotone.monitoring.service.MotorService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(MotorController.class)
@Import({MotorMapperImpl.class, SectorMapperImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MotorControllerTest {

  String path = "/api/monitor/motors";

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  MotorController motorController;
  @Autowired
  MotorMapper motorMapper;
  @Autowired
  SectorMapper sectorMapper;
  @MockBean
  MotorService motorService;

  @Nested
  class Motor_목록_조회 {


    @Test
    void 모터_목록_조회_성공_시_200_반환() throws Exception {
      Motor motor = TestFixtureFactory.createMotorWithSector();
      // given

      List<Motor> motors = List.of(motor);

      Mockito.when(motorService.readMotorList()).thenReturn(motors);

      // when, then
      mockMvc.perform(MockMvcRequestBuilders.get(path))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"))
          .andExpect(jsonPath("data.motors").isArray())
          .andExpect(jsonPath("data.motors[0].motorId").value(motor.getId()))
          .andExpect(jsonPath("data.motors[0].deviceName").value(motor.getDeviceName()))
          .andExpect(jsonPath("data.motors[0].motorName").value(motor.getMotorName()))
          .andExpect(jsonPath("data.motors[0].sectorId").value(motor.getSector().getId()))
          .andExpect(jsonPath("data.motors[0].sectorName").value(motor.getSector().getSectorName()))
          .andExpect(jsonPath("data.motors[0].isOn").value(motor.isOn()));
    }

  }

}
