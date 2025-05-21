package com.Libx.utility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptDecrypt {

    private static final String SECRET_KEY = "my_super_secret_key";
    private static final String SALT = "ssshhhhhhhhhhh!!!!";

    private static final Logger LOGGER = Logger.getLogger(EncryptDecrypt.class.getName());

    public static String encrypt(String strToEncrypt) {
        try {
            // Generate a random 16-byte IV
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Key Derivation using PBKDF2
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            // Cipher setup
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            // Encrypt and prepend IV to ciphertext
            byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedWithIV = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, encryptedWithIV, 0, iv.length);
            System.arraycopy(encrypted, 0, encryptedWithIV, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedWithIV);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Encryption error: ", e);
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            byte[] encryptedIvTextBytes = Base64.getDecoder().decode(strToDecrypt);

            // Extract IV
            byte[] iv = new byte[16];
            System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Extract ciphertext
            int encryptedSize = encryptedIvTextBytes.length - iv.length;
            byte[] encryptedBytes = new byte[encryptedSize];
            System.arraycopy(encryptedIvTextBytes, iv.length, encryptedBytes, 0, encryptedSize);

            // Key Derivation using PBKDF2
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            // Cipher setup
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

            return new String(cipher.doFinal(encryptedBytes), StandardCharsets.UTF_8);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Decryption error: ", e);
        }
        return null;
    }
}
