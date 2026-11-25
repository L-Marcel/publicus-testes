package ufrn.imd.abstracts;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class GenericPage<T extends GenericPage<?>> {
  public static final Logger logger = Logger.getLogger(GenericPage.class.getName());
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
    logger.info("Visiting " + url);
    return this.self();
  }

  public T timeout(Duration duration) {
    this.driver.manage().timeouts().implicitlyWait(duration);
    logger.info("Timeout set to " + duration.getSeconds() + " seconds");
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
    logger.info("Waiting until " + timeout.getSeconds() + " seconds");
    return this.waits.getOrDefault(
      timeout,
      new WebDriverWait(this.driver, timeout)
    ).until(isTrue);
  }

  public T enableAccessibility() {
    logger.info("Waiting until accessibility is enabled");

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
    logger.info("Searching visible input \"" + ariaLabel + "\"");
    return this.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("input[aria-label='" + ariaLabel + "']")
      )
    );
  }

  protected WebElement textareaByLabel(String ariaLabel) {
    logger.info("Searching visible textarea \"" + ariaLabel + "\"");
    return this.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("textarea[aria-label='" + ariaLabel + "']")
      )
    );
  }

  protected void replaceText(WebElement element, String value) {
    element.click();
    element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
    element.sendKeys(value);
  }

  protected WebElement buttonByText(String text) {
    logger.info("Searching visible button \"" + text + "\"");
    return this.until(
      ExpectedConditions.elementToBeClickable(
        By.xpath("//*[@role='button' and normalize-space()='" + text + "']")
      )
    );
  }

  protected WebElement headingByText(String text) {
    logger.info("Searching visible heading \"" + text + "\"");
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

  protected WebElement textByContent(String text) {
    logger.info("Searching visible text \"" + text + "\"");
    return this.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//flt-semantics-host//*[normalize-space()='" + text + "']")
      )
    );
  }

  protected WebElement textContaining(String text) {
    logger.info("Searching visible text containing \"" + text + "\"");
    return this.until(
      ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//flt-semantics-host//*[contains(normalize-space(), '" + text + "')]")
      )
    );
  }

  public T toast(String expectedMessage) {
    logger.info("Searching toast \"" + expectedMessage + "\"");

    WebElement toast = this.until(
      ExpectedConditions.presenceOfElementLocated(
        By.xpath(
          "//*[not(self::html) and not(self::body) and not(self::flt-glass-pane) and contains(normalize-space(), '"
            + expectedMessage + "')]" +
            " | //*[contains(normalize-space(@aria-label), '" + expectedMessage + "')]"
        )
      )
    );

    String content = toast.getAttribute("textContent");
    if (content == null || !content.contains(expectedMessage))
      content = toast.getAttribute("aria-label");

    assertTrue(
      content != null && content.contains(expectedMessage),
      "Expected toast containing \"" + expectedMessage + "\", but got \"" + content + "\""
    );

    logger.info("Verified toast \"" + expectedMessage + "\"");
    return this.self();
  }
}
