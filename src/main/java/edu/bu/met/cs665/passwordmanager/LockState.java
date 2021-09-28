package edu.bu.met.cs665.passwordmanager;

public interface LockState {
  /**
   * This exits the Password Manager, locking all of the logins.
   * @param manager is the manager executing this command.
   */
  void quit(PasswordManager manager);

  /**
   * This logins into the locked password manager.
   * @param manager is the locked instance.
   * @param master is the user provided password.
   * @return true if login was successful.
   */
  boolean login(PasswordManager manager, String master);
}
