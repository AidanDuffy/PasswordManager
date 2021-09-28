package edu.bu.met.cs665.passwordmanager;

public class UnlockedState implements LockState {

  @Override
  public void quit(PasswordManager manager) {
	manager.setState(new LockedState());
  }

  @Override
  public boolean login(PasswordManager manager, String master) {
	return false;
  }
}
