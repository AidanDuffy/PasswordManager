package edu.bu.met.cs665.login;

public class Password {

  private final char[] passwordText;

  public Password(String pass) {
    this.passwordText = pass.toCharArray();
  }

  public Password(char[] passwordText) {
    this.passwordText = passwordText;
  }

  public char[] getChar() {
	return this.passwordText;
  }
}
