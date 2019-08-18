package springstudy.restquery.infra;

import org.springframework.data.jpa.domain.Specification;
import springstudy.restquery.domain.User;
import springstudy.restquery.service.dto.Ex4SearchCriteria;

import java.util.ArrayList;
import java.util.List;

public class Ex4UserSpecificationBuilder {

    private List<Ex4SearchCriteria> params;

    public Ex4UserSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public Ex4UserSpecificationBuilder with(String key, String operation, String prefix, Object value, String suffix) {
        SearchOperation op = SearchOperation.fromSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) {
                boolean startWithAsterisk = prefix.contains("*");
                boolean endWithAsterisk = suffix.contains("*");

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new Ex4SearchCriteria(key, op, value));
        }

        return this;
    }

    public Specification<User> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new Ex4UserSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new Ex4UserSpecification(params.get(i)));
        }

        return result;
    }
}
