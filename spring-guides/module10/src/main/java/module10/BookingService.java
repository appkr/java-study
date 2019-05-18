package module10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private final JdbcTemplate jdbcTemplate;

    public BookingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void book(String... people) {
        for (String person : people) {
            logger.info("Booking {} in a seat...", person);
            jdbcTemplate.update("INSERT INTO bookings(first_name) VALUES (?)", person);
        }
    }

    public List<String> findAllBookings() {
        return jdbcTemplate.query("SELECT first_name FROM bookings",
            (rs, rowNum) -> rs.getString("first_name")
        );
    }

}
