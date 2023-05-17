package ro.jtonic.handson.https.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("keepalive")
public class KeepAliveController {

  @GetMapping
  public String keepAlive() {
    return "is-alive";
  }
}
