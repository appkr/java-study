package dev.appkr.reactiveweb.annotation;

import dev.appkr.reactiveweb.shared.Employee;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EmployeeWebClient {

  WebClient client = WebClient.create("http://localhost:8080");

  public void consume() {
    final Mono<Employee> employeeMono = client.get()
        .uri("/employees/{id}", "1")
        .retrieve()
        .bodyToMono(Employee.class);

    employeeMono.subscribe(System.out::println);

    final Flux<Employee> employeeFlux = client.get()
        .uri("/employees")
        .retrieve()
        .bodyToFlux(Employee.class);

    employeeFlux.subscribe(System.out::println);
  }
}
