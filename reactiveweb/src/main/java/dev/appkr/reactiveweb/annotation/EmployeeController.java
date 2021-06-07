package dev.appkr.reactiveweb.annotation;

import dev.appkr.reactiveweb.shared.Employee;
import dev.appkr.reactiveweb.shared.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeRepository repository;

  @GetMapping(path = "/{id}")
  public Mono<Employee> getEmployeeById(@PathVariable String id) {
    return repository.findEmployeeById(id);
  }

  @GetMapping
  public Flux<Employee> getAllEmployees() {
    return repository.findAllEmployees();
  }

  @PostMapping(path = "/update")
  public Mono<Employee> updateEmployee(@RequestBody Employee employee) {
    return repository.updateEmployee(employee);
  }
}
