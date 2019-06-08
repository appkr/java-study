package springstudy.springcloud.employeesearchserver;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RefreshScope
@RestController
public class EmployeeSearchController {

    EmployeeSearchService service;

    public EmployeeSearchController(EmployeeSearchService service) {
        this.service = service;
    }

    @RequestMapping("/employees/{id}")
    public Employee findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping ("/employees")
    public Collection<Employee> findAll() {
        return service.findAll();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(e.getMessage());
    }
}
