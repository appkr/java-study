package springstudy.events2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springstudy.events2.autoinvoke.InitMethodExampleBean;

@SpringBootApplication
@ComponentScan(basePackages = {"springstudy.events2.event", "springstudy.events2.config"})
public class Events2Application {

    public static void main(String[] args) {
        SpringApplication.run(Events2Application.class, args);
    }

    @Bean(initMethod="init")
    public InitMethodExampleBean exBean() {
        return new InitMethodExampleBean();
    }
}
