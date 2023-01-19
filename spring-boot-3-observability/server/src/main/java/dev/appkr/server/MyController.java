package dev.appkr.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyController {

  final MyUserService myUserService;

  @GetMapping("/user/{userId}")
  String username(@PathVariable("userId") String userId) {
    log.info("Got a request");
    return myUserService.username(userId);
  }
}
