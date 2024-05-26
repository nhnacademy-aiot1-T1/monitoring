package live.aiotone.monitoring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import live.aiotone.monitoring.controller.dto.mapper.MotorMapper;
import live.aiotone.monitoring.controller.dto.mapper.MotorMapperImpl;
import live.aiotone.monitoring.controller.dto.mapper.SectorMapper;
import live.aiotone.monitoring.controller.dto.mapper.SectorMapperImpl;
import live.aiotone.monitoring.controller.dto.mapper.SensorScoreMapperImpl;
import live.aiotone.monitoring.controller.dto.response.MotorDetailResponse;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;
import live.aiotone.monitoring.domain.SensorScore;
import live.aiotone.monitoring.factory.TestFixtureFactory;
import live.aiotone.monitoring.service.MotorService;
import live.aiotone.monitoring.service.SensorService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(MotorController.class)
@AutoConfigureJsonTesters
@Import({MotorMapperImpl.class, SectorMapperImpl.class, SensorScoreMapperImpl.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
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
  @MockBean
  SensorService sensorService;

  @Nested
  class Motor_목록_조회 {

    @Test
    void 모터_목록_조회_성공_시_200_반환() throws Exception {
      Motor motor = TestFixtureFactory.createMotorWithSector();
      // given

      List<Motor> motors = List.of(motor);

      when(motorService.readMotorList()).thenReturn(motors);

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

  @Nested
  class 센서_점수_조회 {

    @Test
    void 센서_점수_조회_성공_시_200_반환() throws Exception {
      // given
      Long motorId = 1L;
      Long sensorId = 1L;
      LocalDateTime start = LocalDateTime.now().minusMinutes(5);
      LocalDateTime end = LocalDateTime.now();
      String startStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(start);
      String endStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(end);
      SensorScore sensorScore = TestFixtureFactory.createSensorScore();
      when(sensorService.readSensorScores(motorId, sensorId, start, end)).thenReturn(List.of(sensorScore));

      // when, then
      mockMvc.perform(MockMvcRequestBuilders.get(path + "/" + motorId + "/sensors/" + sensorId + "/scores")
              .param("start", start.toString())
              .param("end", end.toString()))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"))
          .andExpect(jsonPath("data.startDate").value(startStr))
          .andExpect(jsonPath("data.endDate").value(endStr))
          .andExpect(jsonPath("data.sensorId").value(sensorId))
          .andExpect(jsonPath("data.scores").isArray())
          .andExpect(jsonPath("data.scores[0].score").value(sensorScore.getScore()));
    }

  }

  @Nested
  class Motor_가동률_조회 {

    MotorRunningRateDto motorRunningRateDto = TestFixtureFactory.createMotorRunningRateDto();
    MotorRunningRateDto motorRunningRateDto2 = TestFixtureFactory.createMotorRunningRateDto();
    List<MotorRunningRateDto> motorRunningRateDtos = List.of(motorRunningRateDto, motorRunningRateDto2);

    @Test
    void 모터_가동률_조회_성공_시_200_반환() throws Exception {

      // given
      when(motorService.readMotorRunningRate(Duration.DAY)).thenReturn(motorRunningRateDtos);

      // when, then
      mockMvc.perform(MockMvcRequestBuilders.get(path + "/running-rate"))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"))
          .andExpect(jsonPath("data.rates[0].timestamp").value(motorRunningRateDtos.get(0).getTimestamp()))
          .andExpect(jsonPath("data.rates[0].rate").value(motorRunningRateDtos.get(0).getRate()))
          .andExpect(jsonPath("data.rates[1].timestamp").value(motorRunningRateDtos.get(1).getTimestamp()))
          .andExpect(jsonPath("data.rates[1].rate").value(motorRunningRateDtos.get(1).getRate()));
    }

    @Test
    void 개별_모터_가동률_조회_성공_시_200_반환() throws Exception {
      // given
      Long motorId = 1L;

      when(motorService.readMotorRunningRateById(motorId, Duration.DAY)).thenReturn(motorRunningRateDtos);

      // when, then
      mockMvc.perform(MockMvcRequestBuilders.get(path + "/" + motorId + "/running-rate"))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("status").value("success"))
          .andExpect(jsonPath("data.rates[0].timestamp").value(motorRunningRateDtos.get(0).getTimestamp()))
          .andExpect(jsonPath("data.rates[0].rate").value(motorRunningRateDtos.get(0).getRate()))
          .andExpect(jsonPath("data.rates[1].timestamp").value(motorRunningRateDtos.get(1).getTimestamp()))
          .andExpect(jsonPath("data.rates[1].rate").value(motorRunningRateDtos.get(1).getRate()));
    }

  }

  @Nested
  class 모터_상세정보_조회 {

    @Test
    void 모터_상세정보_조회_성공_시_200_및_MotorDetailResponse_응답() throws Exception {
      // given
      Long motorId = 1L;
      MotorDetailResponse.Sensor sensor1 = MotorDetailResponse.Sensor.builder().sensorId(1L).sensorType("type1").build();
      MotorDetailResponse.Sensor sensor2 = MotorDetailResponse.Sensor.builder().sensorId(2L).sensorType("type2").build();
      MotorDetailResponse expectedResponse = MotorDetailResponse.builder()
          .motorId(motorId)
          .motorName("motor1")
          .sectorId(1L)
          .sectorName("sector1")
          .on(true)
          .normal(true)
          .sensors(Arrays.asList(sensor1, sensor2))
          .build();

      when(motorService.getMotorDetail(motorId)).thenReturn(expectedResponse);

      // when, then
      mockMvc.perform(get("/api/monitor/motors/" + motorId))
          .andExpect(status().isOk())
          .andExpect(jsonPath("data.motorId").value(expectedResponse.getMotorId()))
          .andExpect(jsonPath("data.motorName").value(expectedResponse.getMotorName()))
          .andExpect(jsonPath("data.sectorId").value(expectedResponse.getSectorId()))
          .andExpect(jsonPath("data.sectorName").value(expectedResponse.getSectorName()))
          .andExpect(jsonPath("data.isOn").value(expectedResponse.isOn()))
          .andExpect(jsonPath("data.isNormal").value(expectedResponse.isNormal()))
          .andExpect(jsonPath("data.sensors[0].sensorId").value(sensor1.getSensorId()))
          .andExpect(jsonPath("data.sensors[0].sensorType").value(sensor1.getSensorType()))
          .andExpect(jsonPath("data.sensors[1].sensorId").value(sensor2.getSensorId()))
          .andExpect(jsonPath("data.sensors[1].sensorType").value(sensor2.getSensorType()));
    }

  }


}
