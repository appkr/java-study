package springstudy.restquery.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import springstudy.restquery.service.dto.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class UserPredicateBuilder {

    private List<SearchCriteria> params;

    public UserPredicateBuilder() {
        this.params = new ArrayList<>();
    }

    public UserPredicateBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.size() == 0) {
            return null;
        }

        List<BooleanExpression> predicates = params.stream()
            .map(p -> {
                return new UserPredicate(p).getPredicate();
            })
            .filter(Objects::nonNull)
            .collect(toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for(BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }

        return result;
    }
}
