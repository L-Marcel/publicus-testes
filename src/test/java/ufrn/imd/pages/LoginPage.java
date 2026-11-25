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

  public HomePage login(String login, String password) {
    this.enableAccessibility();
    this.inputByLabel("E-mail ou login").sendKeys(login);
    this.inputByLabel("Senha").sendKeys(password);
    this.buttonByText("Entrar").click();
    logger.info("Logging in");
    return new HomePage(driver);
  }
}
