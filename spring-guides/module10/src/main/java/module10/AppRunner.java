package module10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);
    private final BookingService bookingService;

    public AppRunner(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void run(String... args) throws Exception {
        bookingService.book("Alice", "Bob", "Carol");
        Assert.isTrue(bookingService.findAllBookings().size() == 3,
            "First booking should work with no problem");
        logger.info("Alice, Bob and Carol have been booked");

        try {
            bookingService.book("Chris", "Samuel");
        } catch (RuntimeException ex) {
            logger.info("v--- The following exception is expect because 'Samuel' is too big to the DB ---v");
            logger.error(ex.getMessage());
        }

        for (String person : bookingService.findAllBookings()) {
            logger.info("So far, {} is booked", person);
        }

        logger.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, and Chris was rolled back in the same TX");
        Assert.isTrue(bookingService.findAllBookings().size() == 3,
            "'Samuel' should have triggered a rollback");

        try {
            bookingService.book("Buddy", null);
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage());
        }

        for (String person : bookingService.findAllBookings()) {
            logger.info("So far, {} is booked", person);
        }

        logger.info("You shouldn't see Buddy or null. null violated DB constraint, and Buddy was rolled back in the same Tx");
        Assert.isTrue(bookingService.findAllBookings().size() == 3,
            "'null' should have triggered a rollback");
    }

}
