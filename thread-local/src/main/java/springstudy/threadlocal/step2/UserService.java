package springstudy.threadlocal.step2;

import springstudy.threadlocal.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserService {

    public static void main(String[] args) throws InterruptedException {

        User user1 = new User(1, "foo");
        User.save(user1);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String birthDate = new UserService().getBirthDateFor(Thread.currentThread().getName(), user1.getId());
            }).start();
        }

        Thread.sleep(1000);
    }

    private String getBirthDateFor(String threadName, int userId) {
        User user = User.find(userId);
        LocalDateTime birthDate = user.getBirthDate();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println(threadName + ":" + df.hashCode());
        // NOTE. Each tread has its DateTimeFormatter instance
        // Thread-1:1561084383
        // Thread-9:1889636436
        // Thread-8:124773797
        // Thread-7:1481864668
        // Thread-6:503044489
        // Thread-5:1165898342
        // Thread-4:235072199
        // Thread-2:654647086
        // Thread-0:1535812498
        // Thread-3:77261247

        return birthDate.format(df);
    }
}
