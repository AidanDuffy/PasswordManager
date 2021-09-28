package edu.bu.met.cs665.login;

import edu.bu.met.cs665.pseudorandomgenerator.PasswordCharacterSets;
import edu.bu.met.cs665.pseudorandomgenerator.RandomPasswordGenerator;
import edu.bu.met.cs665.encryption.AESLoginEncryption;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Login {

  private char[] ciphertext;
  private boolean encrypted;
  private Password password;
  private final RandomPasswordGenerator rpg;
  private String username;

  /**
   * Constructor for when a user only has the cipher or is creating a new login.
   * @param rpg the RPG.
   * @param cipherOrNewLogin the ciphertext/new username.
   * @param encrypted true if it is the cipher, false if it is a new login.
   */
  public Login(RandomPasswordGenerator rpg,String cipherOrNewLogin, boolean encrypted) {
    this.encrypted = encrypted;
    if (this.encrypted) {
      this.ciphertext = cipherOrNewLogin.toCharArray();
      this.username = null;
      this.password = null;
      this.rpg = rpg;
    } else {
      this.ciphertext = null;
      this.username = cipherOrNewLogin;
      this.rpg = rpg;
      this.password = createNewPassword();
    }
  }

  /**
   * Constructor for when a user has their desired password when creating a new login.
   * @param rpg the RPG.
   * @param user new username.
   * @param pass is this login's password.
   */
  public Login(RandomPasswordGenerator rpg,String user, char[] pass) {
    this.username = user;
    this.password = new Password(pass);
    this.encrypted = false;
    this.ciphertext = null;
    this.rpg = rpg;
  }

  /**
   * Constructor for when a user has their desired password as an object when creating a new login.
   * @param rpg the RPG.
   * @param user new username.
   * @param pass is this login's password.
   */
  public Login(RandomPasswordGenerator rpg,String user, Password pass) {
    this.username = user;
    this.password = pass;
    this.encrypted = false;
    this.ciphertext = null;
    this.rpg = rpg;
  }

  /**
   * This generates a new random password for the user.
   * @return a new, randomly generated Password object.
   */
  public Password createNewPassword() {
    System.out.println("Generating new password...");
    PasswordCharacterSets set = getCharacterSet();
    int length = getPasswordLength();
    this.rpg.setCharSet(set);
    this.rpg.setLength(length);
    return this.rpg.generateRandomPassword();
  }

  /**
   * This is the helper method for generating a random password, asking the user what type of chars
   * they want the password to contain.
   * @return the selected character set.
   */
  private PasswordCharacterSets getCharacterSet() {
    Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);
    PasswordCharacterSets chosenSet = null;
    while (chosenSet == null) {
      System.out.println("Which set of characters do you want to use (enter 1, 2, or 3):\n1" +
          ". Alphabet\n2.Alphanumeric\n3.Alphanumeric with Symbols...");
      try {
        int choice = Integer.parseInt(s.nextLine());
        switch (choice) {
          case 1:
            chosenSet = PasswordCharacterSets.ALPHA;
            break;
          case 2:
            chosenSet = PasswordCharacterSets.ALPHA_NUMERIC;
            break;
          case 3:
            chosenSet = PasswordCharacterSets.ALPHA_NUMERIC_WITH_SYMBOLS;
            break;
          default:
            break;
        }
      } catch (NumberFormatException ignored) {
      }
    }
    return chosenSet;
  }

  /**
   * The helper method for the random password generator that will get the user's desired
   * password length.
   * @return the user's desired length.
   */
  private int getPasswordLength(){
    Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);
    int length = 0;
    while (length == 0) {
      System.out.println("Enter your password length (8-64): ");
      try {
        int choice = Integer.parseInt(s.nextLine());
        if (choice >= 8 && choice <= 64) {
          length = choice;
        }
      } catch (NumberFormatException ignored) {
      }
    }
    return length;
  }

  public String getUser() {
    if(!encrypted) {
      return this.username;
    }
    return "";
  }

  public String getPass() {
    if(!encrypted) {
      return String.copyValueOf(this.password.getChar());
    }
    return "";
  }

  /**
   * This encrypts the object.
   * @param pass that encrypts the object.
   */
  public void encrypt(String pass) {
    String login = getUser() + "::" + getPass();
    AESLoginEncryption enc = new AESLoginEncryption();
    try {
      this.ciphertext = enc.encrypt(login.getBytes(StandardCharsets.UTF_8), pass).toCharArray();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    this.encrypted = true;
  }

  /**
   * This decrypts the object.
   * @param pass is the password decrypting the object.
   */
  public void decrypt(String pass) {
    if (!encrypted) {
      return;
    }
    try {
      AESLoginEncryption enc = new AESLoginEncryption();
      String plain = enc.decrypt(String.copyValueOf(this.ciphertext), pass);
      String[] login = plain.split("::");
      this.username = login[0];
      this.password = new Password(login[1]);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    this.encrypted = false;
    return;
  }

  public char[] toCharArray() {
    return ciphertext;
  }
}
