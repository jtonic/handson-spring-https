package ro.jtonic.handson.https.resource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.jtonic.handson.https.Application;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {Application.class})
@ContextConfiguration(classes = {SSLContextConfig.class})
class KeepAliveControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void test() {
    ResponseEntity<String> result = testRestTemplate.getForEntity("/keepalive", String.class);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("is-alive", result.getBody());
  }
}
