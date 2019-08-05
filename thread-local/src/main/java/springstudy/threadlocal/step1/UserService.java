package springstudy.threadlocal.step1;

import springstudy.threadlocal.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserService {

    public static void main(String[] args) throws InterruptedException {

        User user1 = new User(1, "foo");
        User.save(user1);

        new Thread(() -> {
            String birthDate = new UserService().getBirthDateFor(user1.getId());
            System.out.println(Thread.currentThread().getName() + ":" + birthDate);
        }).start();

        new Thread(() -> {
            String birthDate = new UserService().getBirthDateFor(user1.getId());
            System.out.println(Thread.currentThread().getName() + ":" + birthDate);
        }).start();

        Thread.sleep(1000);
    }

    private String getBirthDateFor(int userId) {
        User user = User.find(userId);
        LocalDateTime birthDate = user.getBirthDate();

        // NOTE.
        // There will be as many df instance as the number of thread
        // This will result in memory inefficiency
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return birthDate.format(df);
    }
}
