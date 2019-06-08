package springstudy.springcloud.employeesearchserver;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class EmployeeSearchService {

    private static Map<Long, Employee> repository = new HashMap<>();

    public EmployeeSearchService() {
        repository.put(1L, new Employee(1L, "Foo"));
        repository.put(2L, new Employee(2L, "Bar"));
    }

    public Employee findById(Long id) {
        if (! repository.containsKey(id)) {
            throw new RuntimeException("Not Found");
        }
        return repository.get(id);
    }

    public Collection<Employee> findAll() {
        return repository.values();
    }
}
