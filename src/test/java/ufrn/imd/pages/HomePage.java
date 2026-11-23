package ufrn.imd.pages;

import org.openqa.selenium.WebDriver;
import ufrn.imd.abstracts.GenericPage;

public class HomePage extends GenericPage<HomePage> {
  public HomePage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected HomePage self() {
    return this;
  }

  public void menus() {

  }
}
