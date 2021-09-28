package edu.bu.met.cs665;

import edu.bu.met.cs665.passwordmanager.PasswordManager;

import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
// import org.apache.log4j.PropertyConfigurator;

public class Main {

  /**
   * A main method to run examples.
   *
   * @param args not used
   */
  public static void main(String[] args) throws NoSuchFileException {
    Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);
    PasswordManager pm;
    try{
      System.out.println("Input your user ID(1-10)");
      int id = Integer.parseInt(s.nextLine());
      if (id >= 1 && id <= 10) {
        pm = new PasswordManager(id);
      } else {
        pm = new PasswordManager();
      }
    } catch (NumberFormatException ignored) {
      pm = new PasswordManager();
    }

    pm.run();
  }

}
