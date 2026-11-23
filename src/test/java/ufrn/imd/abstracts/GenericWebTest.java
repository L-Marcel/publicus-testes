package ufrn.imd.abstracts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import ufrn.imd.utils.Driver;

public abstract class GenericWebTest {
  protected WebDriver driver;

  @BeforeEach
  void beforeEach() {
    driver = new Driver();
  }

  @AfterEach
  void afterEach() {
    driver.quit();
  }
}
