package springstudy.restquery.api.ex2;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springstudy.restquery.domain.User;
import springstudy.restquery.domain.UserRepository;
import springstudy.restquery.infra.UserSpecifiationBuilder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController("controllerEx2")
@RequestMapping("/api/ex2/users")
public class UserController {

    private static final Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+),", Pattern.UNICODE_CHARACTER_CLASS);

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> search(@RequestParam(value = "search", required = false) String search) {
        UserSpecifiationBuilder builder = new UserSpecifiationBuilder();
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
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
