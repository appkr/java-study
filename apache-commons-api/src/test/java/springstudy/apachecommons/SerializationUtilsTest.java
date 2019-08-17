package springstudy.apachecommons;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class SerializationUtilsTest {

    @Test
    public void test() throws IOException {
        String text = "Hello World";
        byte[] byteArray = Charset.forName("UTF-8").encode(text).array();
        InputStream is = new ByteArrayInputStream(byteArray);

//        SerializationUtils.deserialize(is);

        byte[] serialized = SerializationUtils.serialize("Hello World");
        p(SerializationUtils.deserialize(serialized)); // Hello World
    }

    private void p(Object o) {
        System.out.println(o);
    }
}
