package springstudy.threadlocal.step3;

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
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println(threadName + ":" + df.hashCode());
        // NOTE. Max ten thread but 1000 DateTimeFormatter instances
        // pool-1-thread-1:1590582308
        // pool-1-thread-2:580516132
        // pool-1-thread-5:338537012
        // ...

        return birthDate.format(df);
    }
}
