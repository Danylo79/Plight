package dev.dankom.cipher.aes;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * This example program shows how AES encryption and decryption can be done in Java. Please note
 * that secret key and encrypted text is unreadable binary and hence in the following program we
 * display it in hexadecimal format of the underlying bytes.
 */
public class AESEncryption {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * gets the AES encryption key. In your actual programs, this should be safely stored.
     *
     * @return secKey (Secret key that we encrypt using it)
     * @throws NoSuchAlgorithmException (from KeyGenrator)
     */
    public static SecretKey getSecretEncryptionKey() throws NoSuchAlgorithmException {
        KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("AES");
        aesKeyGenerator.init(128); // The AES key size in number of bits
        SecretKey secKey = aesKeyGenerator.generateKey();
        return secKey;
    }

    /**
     * Encrypts plainText in AES using the secret key
     *
     * @param plainText
     * @param secKey
     * @return byteCipherText (The encrypted text)
     * @throws NoSuchPaddingException (from Cipher)
     * @throws NoSuchAlgorithmException (from Cipher)
     * @throws InvalidKeyException (from Cipher)
     * @throws BadPaddingException (from Cipher)
     * @throws IllegalBlockSizeException (from Cipher)
     */
    public static byte[] encryptText(String plainText, SecretKey secKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        return byteCipherText;
    }

    /**
     * Decrypts encrypted byte array using the key used for encryption.
     *
     * @param byteCipherText
     * @param secKey
     * @return plainText
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decryptText(byte[] byteCipherText, SecretKey secKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }

    /**
     * Convert a binary byte array into readable hex form Old library is deprecated on OpenJdk 11 and
     * this is faster regarding other solution is using StringBuilder Credit
     *
     * @param bytes (in binary)
     * @return hexHash
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
