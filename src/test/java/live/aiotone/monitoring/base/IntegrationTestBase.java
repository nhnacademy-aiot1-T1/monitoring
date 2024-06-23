package live.aiotone.monitoring.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.aiotone.monitoring.setup.DbCleaner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 통합테스트 공통설정 클래스 통합테스트 클래스들은 이 클래스를 상속받아 사용.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class IntegrationTestBase {

  @LocalServerPort
  protected int port;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  DbCleaner cleaner;

  @AfterEach
  void afterTest() {
    cleaner.databaseClean();
  }

}
