package ufrn.imd.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ufrn.imd.abstracts.GenericPage;

public class ArticlePage extends GenericPage<ArticlePage> {
  public ArticlePage(WebDriver driver) {
    super(driver);
  }

  public ArticlePage self() {
    return this;
  }

  public ArticlePage heading(String title) {
    WebElement heading = this.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.id("firstHeading")
      )
    );

    assertEquals(
      title,
      heading.getText()
    );

    return this.self();
  }
}
