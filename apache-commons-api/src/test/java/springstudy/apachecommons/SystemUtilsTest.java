package springstudy.apachecommons;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.apache.commons.lang3.SystemUtils.*;

public class SystemUtilsTest {

    @Test
    public void test() {
        p(System.getenv());
        // {PATH=...}
        p(System.getProperties());
        // {java.runtime.name=Java(TM) SE Runtime Environment, ...}

        p(System.getenv("HOME")); // /Users/appkr
        p(getEnvironmentVariable("HOME", "")); // /Users/appkr

        p(getHostName()); // null

        p(getJavaHome()); // /Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/jre

        p(getJavaIoTmpDir()); // /var/folders/0z/m7gnpmms6r905_9s349vqgqw0000gn/T

        p(getUserDir()); // /Users/appkr/workspace/spring-study/apache-commons-api

        p(getUserHome()); // /Users/appkr

        p(IS_JAVA_1_8); // true
        p(IS_OS_MAC_OSX); // true
        p(JAVA_VERSION); // 1.8.0_211
        p(OS_ARCH); // x86_64
        p(OS_VERSION); // 10.14.6
        p(USER_HOME); // /Users/appkr
        p(USER_NAME); // appkr
        p(USER_TIMEZONE); // ""
    }

    private void p(Object o) {
        System.out.println(o);
    }

    private void pa(Object[] o) {
        System.out.println(ArrayUtils.toString(o));
    }
}
