import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {
    private static final String algo = "AES";
    private static final String trans = "AES";

    public static void encrypt(String key, File input) {
        doCryto(Cipher.ENCRYPT_MODE, key, input);
    }

    public static void decrypt(String key, File input) {
        doCryto(Cipher.DECRYPT_MODE, key, input);
    }

    private static void doCryto(int mode, String key, File input) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), algo);
            Cipher cipher = Cipher.getInstance(trans);
            cipher.init(mode, secretKey);
            FileInputStream inputStream = new FileInputStream(input);
            byte[] inputBytes = new byte[(int) input.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(input);
            outputStream.write(outputBytes);


            inputStream.close();
            outputStream.close();
        } catch (NoSuchPaddingException | IllegalBlockSizeException
                 | NoSuchAlgorithmException | BadPaddingException
                 | InvalidKeyException | IOException e) {
            throw new RuntimeException("Error encrypting/decrypting file", e);
        }
    }
}
