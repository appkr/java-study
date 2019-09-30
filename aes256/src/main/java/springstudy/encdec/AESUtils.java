/**
 * https://choidev-1.tistory.com/67
 */
package springstudy.encdec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESUtils {

    private static final String translation = "AES/CBC/PKCS5Padding";
    private static final String key = "lBdFMFRVDVFuXByNj84HEuecWagMARkW";

    private static String iv;
    private static Key keySpec;
    private static boolean initialized = false;

    private static void init() {
        if (!initialized) {
            iv = key.substring(0, 16);

            byte[] keyBytes = new byte[16];
            byte[] b = iv.getBytes(StandardCharsets.UTF_8);
            int length = b.length;

            if (length > keyBytes.length) {
                length = keyBytes.length;
            }

            System.arraycopy(b, 0, keyBytes, 0, length);
            keySpec = new SecretKeySpec(keyBytes, "AES");

            initialized = true;
        }
    }

    public static String encode(String src) {
        init();

        byte[] encrypted = {};
        try {
            Cipher c = Cipher.getInstance(translation);
            c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

            encrypted = c.doFinal(src.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("AES256 encryption failed: " + e.getMessage());
        }

        return new String(Base64.getEncoder().encode(encrypted));
    }

    public static String decode(String src) {
        init();

        byte[] decrypted = {};
        try {
            Cipher c = Cipher.getInstance(translation);
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

            byte[] byteStr = Base64.getDecoder().decode(src.getBytes());
            decrypted = c.doFinal(byteStr);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException |
                 NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException("AES256 decryption failed: " + e.getMessage());
        }

        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
