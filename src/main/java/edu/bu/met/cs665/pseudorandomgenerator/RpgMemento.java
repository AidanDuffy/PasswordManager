package edu.bu.met.cs665.pseudorandomgenerator;

import java.util.Random;

public interface RpgMemento {
  PasswordCharacterSets getCharSet();

  int getLength();

  Random getRandom();

  int getSeed();

  void setCharSet(PasswordCharacterSets charSet);

  void setLength(int length);

  void setSeed(int seed);

  void setRandom(Random random);
}
