package springstudy.restquery.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;
import springstudy.restquery.domain.User;
import springstudy.restquery.service.dto.SearchCriteria;

public class UserPredicate {

    private final SearchCriteria params;

    public UserPredicate(SearchCriteria params) {
        this.params = params;
    }

    public BooleanExpression getPredicate() {
        PathBuilder<User> entityPath = new PathBuilder<>(User.class, "user");

        if (StringUtils.isNumeric(params.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(params.getKey(), Integer.class);
            int value = Integer.parseInt(params.getValue().toString());
            switch (params.getOperation()) {
                case ":":
                    return path.eq(value);
                case ">":
                    return path.goe(value);
                case "<":
                    return path.loe(value);
            }
        } else {
            StringPath path = entityPath.getString(params.getKey());
            if (params.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(params.getValue().toString());
            }
        }

        return null;
    }
}
