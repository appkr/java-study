package module11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Module11 {

    private static final Logger logger = LoggerFactory.getLogger(Module11.class);

    public static void main(String[] args) {
        SpringApplication.run(Module11.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));

            // fetch all customers
            logger.info("Customers found with findAll():");
            logger.info("-------------------------------");
            repository.findAll().forEach(c -> logger.info(c.toString()));
            logger.info("");

            // fetch an individual customer by id
            repository.findById(1L)
                .ifPresent(c -> {
                    logger.info("Customer found with findById(1L):");
                    logger.info("-------------------------------");
                    logger.info(c.toString());
                });
            logger.info("");

            // fetch customers by last name
            logger.info("Customer found with findByLastName('Bauer'):");
            logger.info("-------------------------------");
            repository.findByLastName("Bauer").forEach(bauer ->
                logger.info(bauer.toString()));
        };
    }
}
