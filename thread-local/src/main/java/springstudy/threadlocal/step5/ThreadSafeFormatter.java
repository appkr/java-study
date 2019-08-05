package springstudy.threadlocal.step5;

import java.time.format.DateTimeFormatter;

public class ThreadSafeFormatter {

    public static ThreadLocal<DateTimeFormatter> dateFormatter = new ThreadLocal<DateTimeFormatter>() {

        @Override
        protected DateTimeFormatter initialValue() {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
    };

//    public static ThreadLocal<DateTimeFormatter> dateFormatter
//        = ThreadLocal.withInitial(() -> new DateTimeFormatter("yyyy-MM-dd"));
}
