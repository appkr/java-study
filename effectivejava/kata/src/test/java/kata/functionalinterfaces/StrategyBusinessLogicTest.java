package kata.functionalinterfaces;

import org.junit.Test;

import static org.junit.Assert.*;

public class StrategyBusinessLogicTest {

    @Test
    public void shouldRunStrategyBusinessLogic() {
        StrategyBusinessLogic.of(i -> i / 2).compute();
        StrategyBusinessLogic.of(i -> i * 4).compute();
        StrategyBusinessLogic.of(i -> i * 2).compute();
    }

    @Test
    public void shouldRunTemplateBusinessLogic() {
        new TemplateBusinessLogic() {
            @Override
            protected void doSomething() {
                System.out.println("FooBar");
            }
        }.compute();

        new TemplateBusinessLogic() {
            @Override
            protected void doSomething() {
                System.out.println("FooBar");
            }
        }.compute();
    }
}