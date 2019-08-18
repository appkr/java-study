package springstudy.restquery.infra;

import org.springframework.data.jpa.domain.Specification;
import springstudy.restquery.domain.User;
import springstudy.restquery.service.dto.Ex4SearchCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class Ex4UserSpecification implements Specification<User> {

    private final Ex4SearchCriteria params;

    public Ex4UserSpecification(Ex4SearchCriteria params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        /**
         * For case-insensitive query
         * @see https://stackoverflow.com/a/4635835
         */
        switch (params.getOperation()) {
            case EQUALITY:
                return builder.equal(builder.lower(root.get(params.getKey())),
                    params.getValue().toString().toLowerCase());
            case NEGATION:
                return builder.notEqual(builder.lower(root.get(params.getKey())),
                    params.getValue().toString().toLowerCase());
            case GREATER_THAN:
                return builder.greaterThan(root.<String>get(params.getKey()), params.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.<String>get(params.getKey()),params.getValue().toString());
            case LIKE:
                return builder.like(builder.lower(root.<String>get(params.getKey())),
                    params.getValue().toString().toLowerCase());
            case STARTS_WITH:
                return builder.like(builder.lower(root.<String>get(params.getKey())),
                    params.getValue().toString().toLowerCase() + "%");
            case ENDS_WITH:
                return builder.like(builder.lower(root.<String>get(params.getKey())),
                    "%" + params.getValue().toString().toLowerCase());
            case CONTAINS:
                return builder.like(builder.lower(root.<String>get(params.getKey())),
                    "%" + params.getValue().toString().toLowerCase() + "%");
            default:
                return null;
        }
    }
}
