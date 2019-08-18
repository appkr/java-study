package springstudy.restquery.api.ex4;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springstudy.restquery.domain.User;
import springstudy.restquery.domain.UserRepository;
import springstudy.restquery.infra.Ex4UserSpecificationBuilder;
import springstudy.restquery.infra.SearchOperation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController("controllerEx4")
@RequestMapping("/api/ex4/users")
public class UserController {

    private static final Pattern pattern;
    static {
        String operationSetExpr = StringUtils.join(SearchOperation.SIMPLE_OPERATION_SET, "|");
        pattern = Pattern.compile("(\\w+)(" + operationSetExpr + ")(\\*?)(\\w+)(\\*?),");
    }

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> findAllBySpecification(@RequestParam(value = "search", required = false) String search) {
        Matcher m = pattern.matcher(search + ",");

        Ex4UserSpecificationBuilder builder = new Ex4UserSpecificationBuilder();
        while(m.find()) {
            builder.with(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5));
        }

        Specification<User> specs = builder.build();

        return repository.findAll(specs);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        repository.save(user);

        return ResponseEntity.noContent().build();
    }
}
