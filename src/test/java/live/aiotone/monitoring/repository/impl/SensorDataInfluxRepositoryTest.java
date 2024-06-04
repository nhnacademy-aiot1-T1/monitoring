package live.aiotone.monitoring.repository.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import java.util.List;
import live.aiotone.monitoring.common.config.InfluxDbSensorProperties;
import live.aiotone.monitoring.domain.SensorData;
import live.aiotone.monitoring.domain.SensorData.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SensorDataInfluxRepositoryTest {

  @Mock
  InfluxDBClient influxDb;

  @Spy
  InfluxDbSensorProperties sensorProperties = new TestInfulxDbSensorProperties();

  @InjectMocks
  SensorDataInfluxRepository sensorDataInfluxRepository;

  @Test
  void findAllSensorDataByDuration() {
    QueryApi mockQueryApi = mock(QueryApi.class);
    when(influxDb.getQueryApi()).thenReturn(mockQueryApi);
    when(mockQueryApi.query(anyString(), eq(SensorData.class))).thenReturn(List.of(SensorData.builder().build()));

    List<SensorData> sensorDataList = sensorDataInfluxRepository.findAllSensorDataByDuration("motorDeviceName", "channel", Duration.DAY);

    Assertions.assertThat(sensorDataList).hasSize(1);
  }

  private static class TestInfulxDbSensorProperties extends InfluxDbSensorProperties {

    public TestInfulxDbSensorProperties() {
      super("measurement", "bucket", "gatewayTagKey", "gatewayTagValue", "motorTagKey", "channelTagKey", 9L);
    }

  }
}