package edu.bu.met.cs665.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;




public class AESLoginEncryption {
  private static final String algorithm = "AES/GCM/NoPadding";
  private static final int LENGTH_OF_TAG_BIT = 120;
  private static final int LENGTH_OF_SALT = 16;
  private static final int LENGTH_OF_IV = 12;

  public String encrypt(byte[] plaintext, String master) throws Exception {
    byte[] salt = CryptoUtils.getRandomNonce(LENGTH_OF_SALT);
    byte[] initial_vector = CryptoUtils.getRandomNonce(LENGTH_OF_IV);
    SecretKey key = CryptoUtils.getAESKeyFromPassword(master.toCharArray(), salt);
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE,key,new GCMParameterSpec(LENGTH_OF_TAG_BIT, initial_vector));
    byte[] encryptedText = cipher.doFinal(plaintext);
    byte[] encryptedwithSaltAndIV = ByteBuffer.allocate(initial_vector.length + salt.length +
        encryptedText.length).put(initial_vector).put(salt).put(encryptedText).array();
    return Base64.getEncoder().encodeToString(encryptedwithSaltAndIV);
  }

  public String decrypt(String ciphertext, String master) throws Exception{
    byte[] decodedb64 = Base64.getDecoder().decode(ciphertext.getBytes(StandardCharsets.UTF_8));
    ByteBuffer bbuf = ByteBuffer.wrap(decodedb64);
    byte[] initial_vector = new byte[LENGTH_OF_IV];
    bbuf.get(initial_vector);
    byte[] salt = new byte[LENGTH_OF_SALT];
    bbuf.get(salt);
    byte[] ciphertextByte = new byte[bbuf.remaining()];
    bbuf.get(ciphertextByte);
    SecretKey key = CryptoUtils.getAESKeyFromPassword(master.toCharArray(),salt);
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE,key,new GCMParameterSpec(LENGTH_OF_TAG_BIT, initial_vector));
    byte[] decryptedText = cipher.doFinal(ciphertextByte);
    return new String(decryptedText,StandardCharsets.UTF_8);
  }
}
