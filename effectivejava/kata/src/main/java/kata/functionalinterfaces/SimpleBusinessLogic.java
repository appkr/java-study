package kata.functionalinterfaces;

public class SimpleBusinessLogic extends TemplateBusinessLogic {

    @Override
    protected void doSomething() {
        System.out.println("From SimpleBusinessLogic");
    }
}
