package dev.appkr.jniExample;

public class JNIExample {

  static {
    System.load(System.getProperty("user.dir") + "/src/main/resources/mynativelib.so");
  }

  public native void greet();

  public static void main(String[] args) {
    final JNIExample app = new JNIExample();
    app.greet();
  }
}
