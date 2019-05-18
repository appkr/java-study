package module14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class Module14 {

    public static void main(String[] args) {
        // Close the application context to shut down the custom ExecutorService
        SpringApplication.run(Module14.class, args).close();
    }

    @Bean
    public Executor taskExecutor() {
        // If the user does not define an Executor bean Spring will create
        // a SimpleAsyncTaskExecutor and use that.
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();

        return executor;
    }
}
