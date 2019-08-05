package springstudy.threadlocal.step5;

import springstudy.threadlocal.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserService {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {

        User user1 = new User(1, "foo");
        User.save(user1);

        for (int i = 0; i < 1000; i++) {
            threadPool.submit(() -> {
                String birthDate = new UserService().getBirthDateFor(Thread.currentThread().getName(), user1.getId());
            });
        }

        Thread.sleep(1000);
    }

    private String getBirthDateFor(String threadName, int userId) {
        User user = User.find(userId);
        LocalDateTime birthDate = user.getBirthDate();
        final DateTimeFormatter df = ThreadSafeFormatter.dateFormatter.get();

        System.out.println(threadName + ":" + df.hashCode());
        // NOTE. Ten thread, one DateTimeFormatter instance per a thread
        // pool-1-thread-1:497094235
        // pool-1-thread-2:864869356
        // pool-1-thread-1:497094235
        // pool-1-thread-2:864869356
        // ...

        return birthDate.format(df);
    }
}
