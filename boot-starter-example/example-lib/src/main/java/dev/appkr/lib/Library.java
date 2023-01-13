package dev.appkr.lib;

public class Library {

    private ExampleProperties properties;

    public Library(ExampleProperties properties) {
        this.properties = properties;
    }

    public String someLibraryMethod() {
        return properties.getKey();
    }
}
