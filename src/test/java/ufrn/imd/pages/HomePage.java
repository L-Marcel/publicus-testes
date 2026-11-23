package ufrn.imd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ufrn.imd.abstracts.GenericPage;

public class HomePage extends GenericPage<HomePage> {
  public HomePage(WebDriver driver) {
    super(driver);
  }

  public HomePage self() {
    return this;
  }

  public ArticlePage search(String search) {
    WebElement input = this.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.id("searchInput")
      )
    );

    input.sendKeys(search);
    input.submit();

    return new ArticlePage(driver);
  }
}
