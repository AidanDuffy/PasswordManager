package edu.bu.met.cs665.pseudorandomgenerator;

public enum PasswordCharacterSets {
  ALPHA("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"),
  ALPHA_NUMERIC("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"),
  ALPHA_NUMERIC_WITH_SYMBOLS("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$" +
	  "%^&*();?><");

  public final String characterSet;

  PasswordCharacterSets(String characterSet) {
    this.characterSet = characterSet;
  }
}
