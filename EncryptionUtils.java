import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncryptionUtils {
    private static final String algo = "AES";
    private static final String trans = "AES/CBC/NoPadding";

    //Todo: Get the encryptionUtils working properly

    /*
   public static void encrypt(String key, File input) {
        doCrypto(Cipher.ENCRYPT_MODE, key, input);
   }x

    public static void decrypt(String key, File input) {
        doCrypto(Cipher.DECRYPT_MODE, key, input);
}*/
/*
    private static void doCrypto(int mode, String key, File input) {
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
*/
    public static void encrypt(String key, InputStream is, OutputStream os) {
        encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, is, os);
    }

    public static void decrypt(String key, InputStream is, OutputStream os) {
        encryptOrDecrypt(key, Cipher.DECRYPT_MODE, is, os);
    }

    public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) {
        try {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            doCopy(cis, os);
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            doCopy(is, cos);
        }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException | IOException | InvalidKeySpecException e) {
            throw new RuntimeException("Error encrypting/decrypting file", e);
        }
    }

    public static void doCopy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            os.write(bytes, 0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }


}
