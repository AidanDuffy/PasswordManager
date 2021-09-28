package edu.bu.met.cs665.passwordmanager;

import edu.bu.met.cs665.login.Login;
import edu.bu.met.cs665.pseudorandomgenerator.RandomPasswordGenerator;
import edu.bu.met.cs665.pseudorandomgenerator.RpgMemento;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.*;

public class PasswordManager {

  private static final String DUMMY_USER = "sAmPlEuSEr";
  private static final char[] DUMMY_PASS = {'p', 'a', 'S', 'S', 'w', '0', 'R', 'd'};

  private File loginFile;
  private ArrayList<Login> logins;
  private HashMap<String, Login> loginsWithNames;
  private String master;
  private final Stack<RpgMemento> mementos = new Stack<>(); //Can only be looked back on in the same instance.
  private RandomPasswordGenerator rpg;
  private LockState state;
  private int userId;

  public PasswordManager() {
    generateUserId();
    this.logins = new ArrayList<>();
    this.loginsWithNames = new HashMap<>();
    this.master = null;
    populateLoginLists();
    createMasterPassword();
    getFile();
    if (!this.loginFile.exists()) {
      try {
        this.loginFile.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    this.rpg = new RandomPasswordGenerator();
    this.state = new UnlockedState();
  }

  public PasswordManager(int id) throws NoSuchFileException {
    this.userId = id;
    this.state = new LockedState();
    getFile();
    this.master = "";
    if (!this.loginFile.exists()) {
      throw new NoSuchFileException("The provided user id does not exist!");
    }
    populateLoginLists();
  }

  private void createMasterPassword() {
    System.out.println("Please enter your desired master password: ");
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    this.master = scanner.nextLine();
  }

  private void getFile() {
    File workingDir = new File(System.getProperty("user.dir"));
    String proj = workingDir.getAbsolutePath();
    this.loginFile = new File(proj + "/login_files/" +
    this.userId + ".txt");
  }

  /**
   * This generates a random user ID, assumes that only 10 profiles could exist.
   */
  private void generateUserId() {
    Random random = new Random();
    this.userId = random.nextInt(10);
  }

  protected Login getDummyLogin() {
    return this.logins.get(this.logins.size() - 1);
  }

  public void lockManager() {
    this.state.quit(this);
    encryptyLogins();
  }

  private void encryptyLogins() {
    for (Login login : logins) {
      login.encrypt(this.master);
    }
    writeEncryptedLogins();
  }

  private void writeEncryptedLogins() {
    FileWriter writer;
    try {
      writer = new FileWriter(this.loginFile, StandardCharsets.UTF_8);
      for (Map.Entry<String, Login> login : this.loginsWithNames.entrySet()) {
        writer.write(login.getKey() + ":");
        writer.write(String.copyValueOf(login.getValue().toCharArray()) + "\n");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
  }

  public void unlockManager(String masterPassword) {
    boolean success = this.state.login(this, masterPassword);
    if (success) {
      if (this.master == null || this.master.equals("")) {
        this.master = masterPassword;
      }
      decryptLogins();
    }
  }

  private void decryptLogins() {
    for (Login login : logins) {
      login.decrypt(this.master);
    }
  }

  private void populateLoginLists() {
    if (this.master != null) {
      Scanner s = null;
      try {
        s = new Scanner(this.loginFile, StandardCharsets.UTF_8);
      } catch (IOException e) {
        e.printStackTrace();
      }
      this.logins = new ArrayList<>();
      this.loginsWithNames = new HashMap<>();
      String[] line;
      Login curLogin;
      while (s != null && s.hasNext()) {
        line = s.nextLine().split(":");
        curLogin = new Login(this.rpg, line[1], true);
        this.logins.add(curLogin);
        this.loginsWithNames.put(line[0], curLogin);
      }
    } else {
      Login dummy = new Login(this.rpg, DUMMY_USER, DUMMY_PASS);
      this.logins.add(dummy);
      this.loginsWithNames.put("Dummy", dummy);
    }
  }

  public void setState(LockState state) {
    this.state = state;
  }

  public void run() {
    Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);
    while (this.state instanceof LockedState) {
      System.out.println("The system is locked. Please enter the master password: ");
      String master = s.nextLine();
      unlockManager(master);
    }
    while (true) {
      System.out.println("Please select an action(Enter 1-3 or 0 to exit):\n1. Access a login\n2." +
          " Create a new login\n3. Delete a login");
      try {
        int choice = Integer.parseInt(s.nextLine());
        if (choice < 0 || choice > 4) {
          continue;
        } else if (choice == 0) {
          System.out.println("Exiting...");
          lockManager();
          break;
        }
        switch (choice) {
          case 1:
            accessLogin(getLogin());
            break;
          case 2:
            createLogin();
            break;
          case 3:
            deleteLogin(getLogin());
            break;
          default:
            break;
        }
      } catch (NumberFormatException ignored) {
      }
    }
  }

  /**
   * This delivers the login that will be access, changed, or deleted.
   *
   * @return the desired login.
   */
  private Login getLogin() {
    System.out.println("From the options below, please select the desired login:");
    if (this.logins.size() <= 1) {
      System.out.println("Please add some first!");
      return null;
    }
    int count = 1;
    ArrayList<String> names = new ArrayList<>();
    for (String name : this.loginsWithNames.keySet()) {
      System.out.println(count + ". " + name);
      count += 1;
      names.add(name);
    }
    Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);
    while (true) {
      try {
        int choice = Integer.parseInt(s.nextLine());
        if (choice < 1 || choice >= count) {
          System.out.println("Invalid integer, try again!");
        } else {
          return this.loginsWithNames.get(names.get(choice - 1));
        }
      } catch (Exception e) {
        System.out.println("Not an int, try again...");
      }

    }
  }

  private void accessLogin(Login login) {
    if (login == null) {
      return;
    }
    System.out.println("Accessing selected login...");
    System.out.println("Username: " + login.getUser());
    System.out.println("Password: " + login.getPass());
  }

  private void deleteLogin(Login login) {
    if (login == null) {
      return;
    }
    System.out.println("Deleting selected login...");
    this.logins.remove(login);
    for (Map.Entry<String, Login> loginEntry : this.loginsWithNames.entrySet()) {
      if (loginEntry.getKey().equals(login)) {
        this.loginsWithNames.remove(loginEntry.getKey(), login);
        return;
      }
    }
  }

  private void createLogin() {
    Scanner s = new Scanner(System.in, StandardCharsets.UTF_8);
    System.out.println("Please enter the name for the new login: ");
    String name = s.nextLine();
    System.out.println("Please enter the username or email for the new login: ");
    String user = s.nextLine();
    this.mementos.add(rpg.getMemento());
    Login login = new Login(this.rpg, user, false);
    this.loginsWithNames.put(name, login);
    this.logins.add(login);
  }
}
