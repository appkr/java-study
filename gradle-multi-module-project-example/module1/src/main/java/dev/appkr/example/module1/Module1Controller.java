package dev.appkr.example.module1;

import dev.appkr.example.common.config.ApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Module1Controller {

  final ApplicationProperties properties;

  @GetMapping("/api/module1")
  public Module1 module1() {
    return new Module1(properties.getModule());
  }

  @AllArgsConstructor
  @Data
  static class Module1 {
    String value;
  }
}
