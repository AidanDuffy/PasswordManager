package edu.bu.met.cs665.pseudorandomgenerator;

import edu.bu.met.cs665.login.Password;

import java.util.Random;

public class RandomPasswordGenerator {

  private int length;
  private PasswordCharacterSets charSet;
  private Random random;
  private int seed;

  public RandomPasswordGenerator() {
    this.charSet = PasswordCharacterSets.ALPHA_NUMERIC;
    this.length = 20;
    this.random = new Random();
    this.seed = this.random.nextInt();
  }

  public RpgMemento getMemento() {
    RpgMemento mem = new RpgMementoInternal();
    mem.setRandom(this.random);
    mem.setLength(this.length);
    mem.setCharSet(this.charSet);
    mem.setSeed(this.seed);
    return mem;
  }

  public void setMemento(RpgMemento memento) {
    RpgMemento mem = memento;
    this.random = mem.getRandom();
    this.length = mem.getLength();
    this.charSet = mem.getCharSet();
    this.seed = mem.getSeed();
  }

  public void setLength(int length) {
    this.length = length;
  }

  public void setCharSet(PasswordCharacterSets charSet) {
    this.charSet = charSet;
  }

  public void setSeed(int seed) {
    this.seed = seed;
    this.random = new Random(this.seed);
  }

  /**
   * Delivers a randomized password based off of the character set and a PRNG.
   * @return the new password object.
   */
  public Password generateRandomPassword() {
    String characters = this.charSet.characterSet;
    int range = characters.length();
    char[] text = new char[this.length];
    for (int i = 0; i < this.length; i += 1) {
      text[i] = characters.charAt(this.random.nextInt(range));
    }
    return new Password(text);
  }

  private static class RpgMementoInternal implements RpgMemento {
    private int length;
    private PasswordCharacterSets charSet;
    private int seed;
    private Random random;


    public PasswordCharacterSets getCharSet() {
      return charSet;
    }

    public int getLength() {
      return length;
    }

    public Random getRandom() {
      return random;
    }

    public int getSeed() {
      return seed;
    }

    public void setCharSet(PasswordCharacterSets charSet) {
      this.charSet = charSet;
    }

    public void setLength(int length) {
      this.length = length;
    }

    public void setSeed(int seed) {
      this.seed = seed;
    }

    public void setRandom(Random random) {
      this.random = random;
    }
  }
}
