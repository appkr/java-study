package springstudy.restquery.infra;

import org.springframework.data.jpa.domain.Specification;
import springstudy.restquery.domain.User;
import springstudy.restquery.service.dto.SearchCriteria;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class UserSpecifiationBuilder {

    private final List<SearchCriteria> params;

    public UserSpecifiationBuilder() {
        this.params = new ArrayList<SearchCriteria>();
    }

    public UserSpecifiationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<User> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
            .map(UserSpecification::new)
            .collect(toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}
