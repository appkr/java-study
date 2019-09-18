package springstudy.fbf;

import org.junit.Test;
import java.nio.charset.Charset;
import static org.junit.Assert.assertEquals;

public class PacketTest {

    @Test
    public void testFormat() {
        Item name = Item.of("name", 10, "홍길동");
        Item phone = Item.of("phone", 11, "01012345678");

        Packet packet = new Packet();
        packet.addItem(name);
        packet.addItem(phone);

        assertEquals("홍길동    01012345678", packet.format());
    }

    @Test
    public void testParse() {
        Packet received = new Packet();
        received.addItem(Item.of("birth", 8));
        received.addItem(Item.of("address", 30));

        // To convert a hex string to byte array
        // @see https://stackoverflow.com/questions/3516021/literal-syntax-for-byte-arrays-using-hex-notation
        //
        // For EUC-KR codepoint
        // @see https://uic.win/ko/charset/show/euc-kr/
        byte[] source = { 0x31, 0x39, 0x37, 0x30, 0x30, 0x31, 0x30, 0x31,                                                                        // 19700101 (8byte)
                         (byte)0xbc, (byte)0xad, (byte)0xbf, (byte)0xef, (byte)0xc6, (byte)0xaf, (byte)0xba, (byte)0xb0, (byte)0xbd, (byte)0xc3, // 서울특별시 (10byte)
                         0x20,                                                                                                                   // 공백 (1byte)
                         (byte)0xc1, (byte)0xdf, (byte)0xb1, (byte)0xb8,                                                                         // 중구 (4byte)
                         0x20,                                                                                                                   // 공백 (1byte)
                         (byte)0xbc, (byte)0xad, (byte)0xbc, (byte)0xd2, (byte)0xb9, (byte)0xae, (byte)0xb5, (byte)0xbf,                         // 서소문동 (8byte)
                         0x20,                                                                                                                   // 공백 (1byte)
                         0x33, 0x37,                                                                                                             // 37 (2byte)
                         0x20, 0x20, 0x20,                                                                                                       // 공백 (3byte)
                                                                                                                                                 // ---------------
                                                                                                                                                 // 38byte
        };

        System.out.println(new String(source, Charset.forName("EUC-KR"))); // 19700101서울특별시 중구 서소문동 37

        // byte[] source2 = "19700101서울특별시 중구 덕수궁길 15   ".getBytes();
        // System.out.println(Arrays.toString(source));
        //      [49, 57, 55, 48, 48, 49, 48, 49, -68, -83, -65, -17, -58, -81, -70, -80, -67, -61, 32, -63, -33, -79, -72, 32, -68, -83, -68, -46, -71, -82, -75, -65, 32, 51, 55, 32, 32, 32]
        // System.out.println(Arrays.toString(source2));
        //      [49, 57, 55, 48, 48, 49, 48, 49, -20, -124, -100, -20, -102, -72, -19, -118, -71, -21, -77, -124, -20, -117, -100, 32, -20, -92, -111, -22, -75, -84, 32, -21, -115, -107, -20, -120, -104, -22, -74, -127, -22, -72, -72, 32, 49, 53, 32, 32, 32]

        received.parse(source);
        System.out.println(received);
    }
}
