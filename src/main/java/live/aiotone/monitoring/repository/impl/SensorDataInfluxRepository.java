package live.aiotone.monitoring.repository.impl;

import static com.influxdb.query.dsl.functions.restriction.Restrictions.field;
import static com.influxdb.query.dsl.functions.restriction.Restrictions.measurement;
import static com.influxdb.query.dsl.functions.restriction.Restrictions.tag;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.dsl.Flux;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import live.aiotone.monitoring.common.config.InfluxDbSensorProperties;
import live.aiotone.monitoring.domain.SensorData;
import live.aiotone.monitoring.domain.SensorData.Duration;
import live.aiotone.monitoring.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 센서 데이터 InfluxDB  Repository.
 */
@Repository
@RequiredArgsConstructor
public class SensorDataInfluxRepository implements SensorDataRepository {

  private final InfluxDBClient influxDb;

  private final InfluxDbSensorProperties sensorProperties;

  @Override
  public List<SensorData> findAllSensorDataByDuration(String motorDeviceName, String channel, Duration duration) {

    Flux flux = Flux.from(sensorProperties.getBucket())
        .range().withStart(duration.getInstant()).withStop(Instant.now())
        .filter(field().equal("value"))
        .filter(measurement().equal(sensorProperties.getMeasurement()))
        .filter(tag(sensorProperties.getGatewayTagKey()).equal(sensorProperties.getGatewayTagValue()))
        .filter(tag(sensorProperties.getMotorTagKey()).equal(motorDeviceName))
        .filter(tag(sensorProperties.getChannelTagKey()).equal(channel))
        .aggregateWindow()
        .withEvery(duration.getPeriod())
        .withAggregateFunction("mean")
        .timeShift(sensorProperties.getTimeShift(), ChronoUnit.HOURS);
    return influxDb.getQueryApi().query(flux.toString(), SensorData.class);
  }
}
