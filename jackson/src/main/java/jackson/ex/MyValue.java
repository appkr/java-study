package jackson.ex;

public class MyValue {

    private String name;
    private int age;

    public MyValue() {
        // Jackson 작동을 위해 Default Constructor 필요함!!
    }

    public MyValue(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
