package ufrn.imd.abstracts;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
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
    logger.info("Acessando " + url);
    return this.self();
  }

  public T timeout(Duration duration) {
    this.driver.manage().timeouts().implicitlyWait(duration);
    logger.info("Tempo limite definido para " + duration.getSeconds() + " segundos");
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
    String description,
    Function<? super WebDriver, ? extends V> isTrue
  ) {
    return this.until(
      Duration.ofSeconds(10),
      description,
      isTrue
    );
  }

  public <V extends Object> V until(
    Duration timeout,
    Function<? super WebDriver, ? extends V> isTrue
  ) {
    return this.until(timeout, null, isTrue);
  }

  public <V extends Object> V until(
    Duration timeout,
    String description,
    Function<? super WebDriver, ? extends V> isTrue
  ) {
    try {
      return this.waits.computeIfAbsent(
        timeout,
        t -> new WebDriverWait(this.driver, t)
      ).until(isTrue);
    } catch (TimeoutException e) {
      String detail = description != null
        ? description
        : e.getMessage() == null
          ? "condição não satisfeita"
          : e.getMessage().lines().findFirst().orElse("condição não satisfeita");

      return fail("Tempo limite excedido (" + timeout.getSeconds() + "s): " + detail);
    }
  }

  public T enableAccessibility() {
    logger.info("Aguardando a habilitação da acessibilidade");

    this.until(
      "acessibilidade do Flutter não foi habilitada",
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
    logger.info("Buscando campo visível \"" + ariaLabel + "\"");
    return this.until(
      "campo \"" + ariaLabel + "\" não encontrado",
      ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector("input[aria-label='" + ariaLabel + "']")
      )
    );
  }

  protected WebElement textareaByLabel(String ariaLabel) {
    logger.info("Buscando área de texto visível \"" + ariaLabel + "\"");
    return this.until(
      "área de texto \"" + ariaLabel + "\" não encontrada",
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
    logger.info("Buscando botão visível \"" + text + "\"");
    return this.until(
      "botão \"" + text + "\" não encontrado ou não clicável",
      ExpectedConditions.elementToBeClickable(
        By.xpath("//*[@role='button' and normalize-space()='" + text + "']")
      )
    );
  }

  protected WebElement headingByText(String text) {
    logger.info("Buscando título visível \"" + text + "\"");
    return this.until(
      "título \"" + text + "\" não encontrado",
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
    logger.info("Buscando texto visível \"" + text + "\"");
    return this.until(
      "texto \"" + text + "\" não encontrado",
      ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//flt-semantics-host//*[normalize-space()='" + text + "']")
      )
    );
  }

  protected WebElement textContaining(String text) {
    logger.info("Buscando texto visível contendo \"" + text + "\"");
    return this.until(
      "texto contendo \"" + text + "\" não encontrado",
      ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//flt-semantics-host//*[contains(normalize-space(), '" + text + "')]")
      )
    );
  }

  public T toast(String expectedMessage) {
    logger.info("Buscando toast \"" + expectedMessage + "\"");

    WebElement toast = this.until(
      "toast \"" + expectedMessage + "\" não apareceu",
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
      "Esperava toast contendo \"" + expectedMessage + "\", mas obteve \"" + content + "\""
    );

    logger.info("Toast verificado \"" + expectedMessage + "\"");
    return this.self();
  }
}
