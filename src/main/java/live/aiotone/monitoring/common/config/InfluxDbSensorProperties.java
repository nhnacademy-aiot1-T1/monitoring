package live.aiotone.monitoring.common.config;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * InfluxDb 에서 센서 데이터를 조회하기 위한 설정 정보.
 */
@Getter
@ConfigurationProperties(prefix = "monitoring.influxdb.sensor")
@ConstructorBinding
public class InfluxDbSensorProperties {

  /**
   * 센서 데이터를 조회할 때 사용할 측정값 이름.
   */
  private final String measurement;
  /**
   * 센서 데이터를 조회할 때 사용할 버킷 이름.
   */
  private final String bucket;
  /**
   * 센서 데이터를 조회할 때 사용할 게이트웨이 태그 키.
   */
  private final String gatewayTagKey;
  /**
   * 센서 데이터를 조회할 때 사용할 모터 태그 키.
   */
  private final String motorTagKey;
  /**
   * 센서 데이터를 조회할 때 사용할 센서 태그 키.
   */
  private final String channelTagKey;

  /**
   * 센서 데이터를 조회할 때 UTC 시간을  Local 시간으로 변경하기 위한 시간차.
   */
  private final Long timeShift;


  private InfluxDbSensorProperties(@DefaultValue("rawData") String measurement, @DefaultValue("ai") String bucket,
      @DefaultValue("gateway") String gatewayTagKey,
      @DefaultValue("motor") String motorTagKey,
      @DefaultValue("channel") String channelTagKey,
      @DefaultValue("9") Long timeShift) {
    this.measurement = measurement;
    this.bucket = bucket;
    this.gatewayTagKey = gatewayTagKey;
    this.motorTagKey = motorTagKey;
    this.channelTagKey = channelTagKey;
    this.timeShift = timeShift;
  }


}
