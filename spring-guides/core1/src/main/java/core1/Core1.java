package core1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Core1 {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Core1.class, args);
        Greeting greeting = (Greeting) ctx.getBean(Greeting.class);
        System.out.println(greeting.hello());
    }

    @Bean
    public Greeting greeting() {
        return new Greeting();
    }

    static class Greeting {
        public String hello() {
            return "Hello World";
        }
    }
}
