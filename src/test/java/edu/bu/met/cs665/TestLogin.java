package edu.bu.met.cs665;

import edu.bu.met.cs665.login.Login;
import edu.bu.met.cs665.login.Password;
import edu.bu.met.cs665.pseudorandomgenerator.RandomPasswordGenerator;
import org.junit.Assert;
import org.junit.Test;


public class TestLogin {

  public TestLogin() {
  }

  @Test
  public void testLoginConstructor() {
    RandomPasswordGenerator rpg = new RandomPasswordGenerator();
    Password p = new Password("password");
    Login login1 = new Login(rpg, "Aidan", p);
    Assert.assertEquals("Aidan", login1.getUser());
    Assert.assertEquals("password",login1.getPass());
  }

  @Test
  public void testLoginEncrypt() {
    RandomPasswordGenerator rpg = new RandomPasswordGenerator();
    String master = "hwlllek;jasgh;w";
    Password p = new Password("password");
    Login login1 = new Login(rpg, "Aidan", p);
    Assert.assertEquals("password",login1.getPass());
    login1.encrypt(master);
    Assert.assertNotSame(p.toString(),login1.getPass());
  }

  @Test
  public void testLoginDecrypt() {
    RandomPasswordGenerator rpg = new RandomPasswordGenerator();
    String master = "hwlllek;jasgh;w";
    Password p = new Password("password");
    Login login1 = new Login(rpg, "Aidan", p);
    Assert.assertEquals("password",login1.getPass());
    login1.encrypt(master);
    Assert.assertNotSame(p.toString(),login1.getPass());
    login1.decrypt(master);
    Assert.assertEquals("password",login1.getPass());
  }

}
