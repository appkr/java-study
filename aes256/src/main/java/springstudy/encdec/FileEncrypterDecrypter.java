package springstudy.encdec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncrypterDecrypter {

    private final Cipher cipher;
    private SecretKey secretKey;
    private String transformation;

    public FileEncrypterDecrypter(SecretKey secretKey, String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.secretKey = secretKey;
        this.cipher = Cipher.getInstance(transformation);
    }

    public void encrypt(String content, String fileName) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
            fileOut.write(iv);
            cipherOut.write(content.getBytes());
        } catch (FileNotFoundException e) {
            //
        } catch (IOException e) {
            //
        }
    }

    public String decrypt(String fileName) {
        String content;

        try (FileInputStream fileIn = new FileInputStream(fileName)) {
            byte[] fileIV = new byte[16];
            fileIn.read(fileIV);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIV));

            try (CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                 InputStreamReader inputReader = new InputStreamReader(cipherIn);
                 BufferedReader reader = new BufferedReader(inputReader)) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
            }

            return content;
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }
}
