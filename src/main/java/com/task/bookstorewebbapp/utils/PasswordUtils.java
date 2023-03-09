package com.task.bookstorewebbapp.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public final class PasswordUtils {

  private static final int SALT_LENGTH = 128;
  private static final String ALGORITHM_NAME = "PBKDF2WithHmacSHA512";
  private static final String RANDOM_ALGORITHM = "SHA1PRNG";
  private static final int ITERATIONS = 200000;
  private static final int KEY_LENGTH = 512;
  private static final int SALT_DIVIDER = KEY_LENGTH / 8 * 2;
  private static final Logger LOGGER = LogManager.getLogger(PasswordUtils.class.getName());

  private PasswordUtils() {
  }


  /**
   * @param password password that need to be encoded.
   * @return encoded password with length of 160.
   */
  public static String encodePassword(String password) {
    String toRet = password;
    byte[] salt;
    try {
      salt = getSalt();
      byte[] hashedPasswordBytes = hashPasswordToBytes(password.toCharArray(), salt);
      toRet = Hex.encodeHexString(hashedPasswordBytes) + Hex.encodeHexString(salt);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      LOGGER.error("Couldn't encode password due to" + e.getMessage(), e);
    }
    return toRet;
  }

  /**
   * @param passwordCharArray charArray of password that need to be encoded.
   * @param salt              salt.
   * @return byte array with 64 length of hashed password.
   */
  private static byte[] hashPasswordToBytes(final char[] passwordCharArray, final byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM_NAME);
    PBEKeySpec keySpec = new PBEKeySpec(passwordCharArray, salt, ITERATIONS, KEY_LENGTH);
    SecretKey key = skf.generateSecret(keySpec);
    return key.getEncoded();
  }


  /**
   * @return salt 16 length.
   */
  private static byte[] getSalt() throws NoSuchAlgorithmException {
    SecureRandom sr = SecureRandom.getInstance(RANDOM_ALGORITHM);
    byte[] salt = new byte[SALT_LENGTH / 8];
    sr.nextBytes(salt);
    return salt;
  }

  /**
   * @param password        not encoded password that need check.
   * @param encodedPassword encoded password.
   * @return true if passwords equals.
   */
  public static boolean checkPassword(String password, String encodedPassword) {
    boolean toRet = false;
    String passwordHexed = encodedPassword.substring(0, SALT_DIVIDER);
    byte[] salt;
    try {
      salt = Hex.decodeHex(encodedPassword.substring(SALT_DIVIDER).toCharArray());
      byte[] hashedPasswordBytes = hashPasswordToBytes(password.toCharArray(), salt);
      toRet = Hex.encodeHexString(hashedPasswordBytes).equals(passwordHexed);
    } catch (DecoderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      LOGGER.error("Couldn't check passwords due to" + e.getMessage(), e);
    }
    return toRet;
  }

}
