import java.util.UUID;

public class UuidApp {

    public static void main(String[] args) {
        UUID.randomUUID(); // 55f9dc9f-b278-4600-a2fd-4afcc9392068

        // Hex to Long: 82449568178280
        Long.parseLong("4afcc9392068", 16);
        // Long to Hex: 4afcc9392068
        Long.toHexString(82449568178280L);

        // Hex to Int: 16
        Integer.parseInt("10", 16);
        // Int to Hex: 10
        Integer.toHexString(16);

        // Parse int
        Integer.valueOf(0x10); // 16
        Integer.valueOf(010); // 8
        Integer.valueOf(0b11); // 3
    }
}

