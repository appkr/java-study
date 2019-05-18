package module2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Module2 {

    public static void main(String[] args) {
        SpringApplication.run(Module2.class, args);
    }

}
