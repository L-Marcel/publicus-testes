package ufrn.imd.abstracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class GenericPageWithMenu<T extends GenericPageWithMenu<T>> extends GenericPage<T> {
  protected GenericPageWithMenu(WebDriver driver) {
    super(driver);
  }

  protected List<WebElement> navigationItems() {
    logger.info("Buscando itens de menu visíveis");
    return this.until(
      "itens de menu visíveis não encontrados",
      ExpectedConditions.visibilityOfAllElementsLocatedBy(
        By.cssSelector("[role='button'][aria-current]")
      )
    );
  }

  public T menus(String... expected) {
    List<String> actual = this.navigationItems()
      .stream()
      .map(item -> item.getAttribute("textContent").lines().findFirst().orElse("").trim())
      .toList();

    assertEquals(List.of(expected), actual);
    return this.self();
  }
}
