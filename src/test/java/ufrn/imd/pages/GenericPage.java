package ufrn.imd.pages;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import org.openqa.selenium.WebDriver;
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
}
