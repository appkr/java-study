package kata.enums;

import kata.enums.EnumAsASingleton;
import org.junit.Test;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class EnumAsASingletonTest {

    private final Logger log = Logger.getGlobal();

    @Test
    public void thereMustBeOnlyOneInstance() throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<EnumAsASingleton> futureA = es.submit(() -> {
            return EnumAsASingleton.INSTANCE;
        });

        Future<EnumAsASingleton> futureB = es.submit(() -> {
            return EnumAsASingleton.INSTANCE;
        });
        EnumAsASingleton sutA = futureA.get();
        EnumAsASingleton sutB = futureA.get();

        System.out.println(sutA.hashCode());
        System.out.println(sutB.hashCode());
        assertTrue(sutA == sutB);
    }
}