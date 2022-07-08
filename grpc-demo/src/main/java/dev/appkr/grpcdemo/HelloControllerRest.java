package dev.appkr.grpcdemo;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HelloControllerRest {

  HelloClientRpc helloClient;

  @GetMapping("/hello")
  public Map<String, Object> hello(@RequestParam(value = "name", required = false) String name) {
    final String message = helloClient.hello(name);
    return Map.of("message", message);
  }
}
