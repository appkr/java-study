package dev.appkr.lib;

public class ExampleProperties {

  public static final String DEFAULT_KEY = "bar";

  String key = DEFAULT_KEY;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return "ExampleProperties{" +
        "key='" + key + '\'' +
        '}';
  }
}
