package springstudy.threadlocal.step4;

import springstudy.threadlocal.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserService {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

        System.out.println(threadName + ":" + df.hashCode());
        // NOTE. Only one DateTimeFormatter but it is not thread-safe
        // If we introduce lock for thread-safety, it induces slow performance
        // pool-1-thread-2:1040261576
        // pool-1-thread-4:1040261576
        // pool-1-thread-1:1040261576
        // ...

        return birthDate.format(df);
    }
}
