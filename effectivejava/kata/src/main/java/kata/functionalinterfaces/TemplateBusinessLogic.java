package kata.functionalinterfaces;

public abstract class TemplateBusinessLogic {
    
    public void compute() {
        System.out.println("x");
        doSomething();
        System.out.println("a");
        System.out.println("---");
    }

    protected abstract void doSomething();
}
