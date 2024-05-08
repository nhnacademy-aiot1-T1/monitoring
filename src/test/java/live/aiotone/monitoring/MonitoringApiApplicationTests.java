package live.aiotone.monitoring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MonitoringApiApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertThatNoException().isThrownBy(() -> {
		});
	}

}
