package springstudy.tobitv.ep1;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class DynamicDispatch {

    static abstract class Service {
        abstract void run();
    }

    static class MyService1 extends Service {
        @Override
        void run() {
            log.info("run1");
        }
    }

    static class MyService2 extends Service {
        @Override
        void run() {
            log.info("run2");
        }
    }

    public static void main(String[] args) {
        // Dynamic Dispatch - 컴파일 타임에 구체 타입을 알 수 없음
        List<Service> svc = Arrays.asList(new MyService1(), new MyService2());
        svc.forEach(s -> s.run());
    }
}
