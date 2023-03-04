package dev.appkr.example.module2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Module2Controller {

  ServerProperties properties;

  @GetMapping("/api/module2")
  public Module2 module1() {
    return new Module2("module2");
  }

  @AllArgsConstructor
  @Data
  static class Module2 {
    String value;
  }
}
