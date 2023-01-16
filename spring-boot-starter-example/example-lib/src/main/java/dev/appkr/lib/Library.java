package dev.appkr.lib;

public class Library {

    private ExampleBean bean;
    private ExampleProperties properties;

    public Library(ExampleBean bean, ExampleProperties properties) {
        this.bean = bean;
        this.properties = properties;
    }

    public String someLibraryMethod() {
        return bean.getValue() + " " + properties.getKey();
    }
}
