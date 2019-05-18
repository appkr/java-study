package module5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Module5 implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Module5.class);

    public static void main(String[] args) {
        SpringApplication.run(Module5.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public void run(String... args) throws Exception {
        log.info("Creating tables");
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
            "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
            .stream()
            .map(name -> name.split(" "))
            .collect(Collectors.toList());

        splitUpNames.forEach(name -> {
            String msg = String.format("Inserting customer record for %s %s", name[0], name[1]);
            log.info(msg);
        });

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?, ?)", splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");
        String SELECT_QUERY = "SELECT id, first_name, last_name FROM customers where first_name = ?";
        Object[] BINDING = new Object[] {"Josh"};
        jdbcTemplate.query(SELECT_QUERY, BINDING, (rs, rowNum) -> new Customer(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name")
            )
        ).forEach(customer -> log.info(customer.toString()));

//        jdbcTemplate.query(SELECT_QUERY, BINDING, new RowMapper<Customer>() {
//            @Override
//            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new Customer(
//                    rs.getLong("id"),
//                    rs.getString("first_name"),
//                    rs.getString("last_name")
//                );
//            }
//        }).forEach(customer -> log.info(customer.toString()));
    }
}
