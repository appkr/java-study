package kata.functionalinterfaces;

public class ComplexBusinessLogic extends TemplateBusinessLogic {

    @Override
    protected void doSomething() {
        System.out.println("From ComplexBusinessLogic");
    }
}
