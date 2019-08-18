package springstudy.restquery.api.ex1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springstudy.restquery.domain.IUserDao;
import springstudy.restquery.domain.User;
import springstudy.restquery.service.dto.SearchCriteria;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController("controllerEx1")
public class UserController {

    private static final Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+),");

    private final IUserDao userDao;

    public UserController(IUserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/api/users")
    public List<User> findAll(@RequestParam(value = "search", required = false) String search) {
        List<SearchCriteria> params = new ArrayList<>();
        if (search != null) {
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }

        return userDao.searchUser(params);
    }

    @PostMapping("/api/users")
    @Transactional
    public ResponseEntity createUser(@RequestBody User user) {
        userDao.save(user);

        return ResponseEntity.noContent().build();
    }
}
