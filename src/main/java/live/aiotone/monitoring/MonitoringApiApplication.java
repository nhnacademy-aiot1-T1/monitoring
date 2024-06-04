package live.aiotone.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan("live.aiotone.monitoring.common.config")
@EnableScheduling
public class MonitoringApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MonitoringApiApplication.class, args);
  }
}
