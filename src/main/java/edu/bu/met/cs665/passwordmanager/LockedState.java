package edu.bu.met.cs665.passwordmanager;

import edu.bu.met.cs665.login.Login;

public class LockedState implements LockState {

  @Override
  public void quit(PasswordManager manager) {
    return;
  }

  @Override
  public boolean login(PasswordManager manager, String master) {
    Login dummy = manager.getDummyLogin();
    dummy.decrypt(master);
    boolean success = dummy.getUser().equals("sAmPlEuSEr") && dummy.getPass().equals("paSSw0Rd");
    if (success) {
      manager.setState(new UnlockedState());
    }
    return success;
  }
}
