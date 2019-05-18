package module4;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class Module4 {

    public static void main(String[] args) {
        Instant instant = Instant.now();
        OffsetDateTime isoDateTime = OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println("The current local time is: " + isoDateTime);

        Greeter greeter = new Greeter();
        System.out.println(greeter.sayHello());
    }
}
