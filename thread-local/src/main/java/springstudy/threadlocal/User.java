package springstudy.threadlocal;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static Map<Integer, User> storage = new HashMap<>();

    private int id;
    private String name;
    private LocalDateTime birthDate;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.birthDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public static boolean save(User user) {
        try {
            storage.put(user.getId(), user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static User find(int userId) {
        User user = storage.get(userId);
        if (user == null) {
            throw new RuntimeException("User not found: userId=" + userId);
        }

        return user;
    }
}
