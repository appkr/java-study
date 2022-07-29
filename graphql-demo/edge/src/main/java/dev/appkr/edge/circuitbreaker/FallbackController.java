package dev.appkr.edge.circuitbreaker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FallbackController {

  @GetMapping("/whenDownstreamFail")
  public ResponseEntity<Map<String, String>> fallback() {
    return ResponseEntity.ok(Map.of("foo", "bar"));
  }
}
