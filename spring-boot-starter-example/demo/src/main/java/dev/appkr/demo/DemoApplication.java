package dev.appkr.demo;

import dev.appkr.autoconfigure.ExampleProperties;
import dev.appkr.lib.ExampleBean;
import dev.appkr.lib.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  Library aLibrary(ExampleBean bean, ExampleProperties props) {
    return new Library(bean, props);
  }

  @Component
  static class DemoEventListener {

    @Autowired Library library;

    @EventListener
    public void handleContextStart(ApplicationReadyEvent e) {
      System.out.println(library.someLibraryMethod());
    }
  }
}
