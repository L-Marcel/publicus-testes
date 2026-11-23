package ufrn.imd.pages;

import org.openqa.selenium.WebDriver;
import ufrn.imd.abstracts.GenericPage;

public class LoginPage extends GenericPage<LoginPage> {
  public LoginPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected LoginPage self() {
    return this;
  }
}
