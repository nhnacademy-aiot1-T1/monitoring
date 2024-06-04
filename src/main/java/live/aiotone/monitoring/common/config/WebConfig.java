package live.aiotone.monitoring.common.config;

import live.aiotone.monitoring.common.config.converter.Converters;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new Converters.MotorRunningLogDurationConverter());
    registry.addConverter(new Converters.SensorDataDurationConverter());
  }

}
