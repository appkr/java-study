package dev.appkr.reactiveweb.functional;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import dev.appkr.reactiveweb.shared.Employee;
import dev.appkr.reactiveweb.shared.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class EmployeeRouter {

  private final EmployeeRepository repository;

  @Bean
  RouterFunction<ServerResponse> getEmployeeRoute() {
    return route(GET("/functional/employees/{id}"), req -> {
      return ok().body(repository.findEmployeeById(req.pathVariable("id")), Employee.class);
    });
  }

  @Bean
  RouterFunction<ServerResponse> allEmployeesRoute() {
    return route(GET("/functional/employees/"), req -> {
      return ok().body(repository.findAllEmployees(), Employee.class);
    });
  }

  @Bean
  RouterFunction<ServerResponse> updateEmployeeRoute() {
    return route(POST("/functional/employees/update"), req -> {
      return req.body(toMono(Employee.class))
          .doOnNext(repository::updateEmployee)
          .then(ok().build());
    });
  }

//  @Bean
//  RouterFunction<ServerResponse> composedRoutes() {
//    return route(GET("/employees"), req ->
//            ok().body(repository.findAllEmployees(), Employee.class))
//        .and(route(GET("/employees/{id}"), req ->
//            ok().body(repository.findEmployeeById(req.pathVariable("id")), Employee.class)))
//        .and(route(POST("/employees/update"), req ->
//            req.body(toMono(Employee.class)).doOnNext(repository::updateEmployee).then(ok().build())));
//  }
}
