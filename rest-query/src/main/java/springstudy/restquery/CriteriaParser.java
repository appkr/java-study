package springstudy.restquery;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaParser {

    private static Pattern pattern;
    static {
        String operationSetExpr = StringUtils.join(SearchOperation.SIMPLE_OPERATION_SET, "|");
        pattern = Pattern.compile("^(\\w+)(" + operationSetExpr + ")(\\*?)(\\w+)(\\*?)$");
    }

    private static Map<String, Operator> ops;
    static {
        Map<String, Operator> tempMap = new HashMap<>();
        tempMap.put("AND", Operator.AND);
        tempMap.put("OR", Operator.OR);
        tempMap.put("or", Operator.OR);
        tempMap.put("and", Operator.AND);

        ops = Collections.unmodifiableMap(tempMap);
    }

    private enum Operator {
        OR(1), AND(2);

        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    private static boolean isHigherPrecedenceOperator(String currOp, String prevOp) {
        return (ops.containsKey(prevOp) && ops.get(prevOp).precedence >= ops.get(currOp).precedence);
    }

    public Deque<?> parse(String searchParam) {

        Deque<Object> output = new LinkedList<>();
        Deque<String> stack = new LinkedList<>();

        List<String> tokens = Arrays.asList(searchParam.split("\\s+"));
        tokens.forEach(token -> {
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && isHigherPrecedenceOperator(token, stack.peek())) {
                    output.push(stack.pop().equalsIgnoreCase(SearchOperation.OR_OPERATOR)
                        ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
                }

                stack.push(token.equalsIgnoreCase(SearchOperation.OR_OPERATOR)
                    ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
            } else if (token.equals(SearchOperation.LEFT_PARANTHESIS)) {
                stack.push(SearchOperation.LEFT_PARANTHESIS);
            } else if (token.equals(SearchOperation.RIGHT_PARANTHESIS)) {
                while (!stack.peek().equals(SearchOperation.LEFT_PARANTHESIS)) {
                    output.push(stack.pop());
                }
                stack.pop();
            } else {
                Matcher m = pattern.matcher(token);
                while (m.find()) {
                    output.push(new SpecSearchCriteria(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5)));
                }
            }
        });

        while (!stack.isEmpty()) {
            output.push(stack.pop());
        }

        return output;
    }
}
