package ufrn.imd.abstracts;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class GenericPage<T extends GenericPage<?>> {
  private Map<Duration, WebDriverWait> waits;
  protected WebDriver driver;

  protected GenericPage(WebDriver driver) {
    this.waits = new LinkedHashMap<>();
    this.driver = driver;
    this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    this.driver.manage().window().maximize();
  }

  protected abstract T self();

  public T navigate(String url) {
    driver.get(url);
    return this.self();
  }

  public T timeout(Duration duration) {
    this.driver.manage().timeouts().implicitlyWait(duration);
    return this.self();
  }

  public <V extends Object> V until(
    Function<? super WebDriver, ? extends V> isTrue
  ) {
    return this.until(
      Duration.ofSeconds(10),
      isTrue
    );
  }

  public <V extends Object> V until(
    Duration timeout,
    Function<? super WebDriver, ? extends V> isTrue
  ) {
    return this.waits.getOrDefault(
      timeout,
      new WebDriverWait(this.driver, timeout)
    ).until(isTrue);
  }

  public T enableAccessibility() {
    this.until(
      executor -> (Boolean) ((JavascriptExecutor) executor).executeScript(
        "const host = document.querySelector('flt-semantics-host');" +
          "if (host && host.children.length > 0) return true;" +
          "const placeholder = document.querySelector('flt-semantics-placeholder');" +
          "if (placeholder) placeholder.click();" +
          "return Boolean(host && host.children.length > 0);"
      )
    );

    return this.self();
  }

  protected WebElement inputByLabel(String ariaLabel) {
    return this.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("input[aria-label='" + ariaLabel + "']")
      )
    );
  }

  protected WebElement buttonByText(String text) {
    return this.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//*[@role='button' and normalize-space()='" + text + "']")
      )
    );
  }

  protected WebElement headingByText(String text) {
    return this.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.xpath(
          "//header[normalize-space(@aria-label)='" + text + "']" +
            " | //*[@role='heading' and normalize-space()='" + text + "']" +
            " | //flt-semantics/span[normalize-space()='" + text + "']"
        )
      )
    );
  }
}
