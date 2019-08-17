package springstudy.apachecommons;

import org.apache.commons.lang3.arch.Processor;
import org.junit.Test;

import static org.apache.commons.lang3.ArchUtils.getProcessor;

public class ArchUtilsTest {

    @Test
    public void test() {
        Processor processor = getProcessor();

        p(processor.getArch()); // BIT_64
        p(processor.getType()); // X86
        p(processor.is32Bit()); // false
        p(processor.is64Bit()); // true
        p(processor.isIA64()); // false
        p(processor.isPPC()); // false
        p(processor.isX86()); // true
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
