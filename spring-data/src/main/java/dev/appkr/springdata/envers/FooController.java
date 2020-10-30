package dev.appkr.springdata.envers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FooController {

  private final FooService service;

  @GetMapping("/foo/{id}")
  public FooDto getFoo(@PathVariable("id") Long id) {
    return service.getFoo(id);
  }

  @GetMapping("/foo/{id}/revisions")
  public FooDto getLatestRevision(@PathVariable("id") Long id) {
    return service.getLatestRevision(id);
  }

  @PostMapping("/foo")
  public FooDto createFoo(@RequestBody FooDto dto) {
    return service.createFoo(dto);
  }

  @PutMapping("/foo/{id}")
  public void updateFoo(@PathVariable("id") Long id, @RequestBody FooDto dto) {
    service.updateFoo(id, dto);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handle(EntityNotFoundException e, HttpServletRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("timestamp", OffsetDateTime.now().toString());
    error.put("error", "entity-not-found");
    error.put("path", request.getRequestURI());
    error.put("message", e.getMessage());

    return error;
  }
}
