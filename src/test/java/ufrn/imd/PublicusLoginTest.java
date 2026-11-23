package ufrn.imd;

import org.junit.jupiter.api.Test;
import ufrn.imd.abstracts.GenericWebTest;
import ufrn.imd.pages.LoginPage;

public class PublicusLoginTest extends GenericWebTest {
  private final String url = "https://testeihc.imd.ufrn.br/publicus/";

  @Test
  void mustLogin() {
    LoginPage home = new LoginPage(driver);
    home.navigate(url).login("r", "r");
  }
}
