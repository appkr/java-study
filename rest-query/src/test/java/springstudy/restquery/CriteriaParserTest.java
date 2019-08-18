package springstudy.restquery;

import java.util.function.Function;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import springstudy.restquery.domain.User;

import java.util.Deque;

public class CriteriaParserTest {

    @Test
    public void testParser1() {
        String search = "( firstName:john OR firstName:tom ) AND age>22";

        CriteriaParser parser = new CriteriaParser();
        final Deque<?> postFixedExprStack = parser.parse(search);
        final Function<SpecSearchCriteria, Specification<User>> converter = UserSpecification::new;

        GenericSpecificationsBuilder<User> builder = new GenericSpecificationsBuilder<>();
        final Specification<User> specs = builder.build(postFixedExprStack, converter);

        System.out.println(specs);
    }

    @Test
    public void testParser2() {
        GenericSpecificationsBuilder<User> builder = new GenericSpecificationsBuilder<>();
        Function<SpecSearchCriteria, Specification<User>> converter = UserSpecification::new;

        builder.with("firstName", ":", "john", null, null);
        builder.with("'", "lastName", ":", "doe", null, null);

        final Specification<User> specs = builder.build(converter);

        System.out.println(specs);
    }
}