package springstudy.springcloud.employeesearchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmployeeSearchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeSearchServerApplication.class, args);
    }

}
