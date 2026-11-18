package ufrn.imd;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ufrn.imd.pages.HomePage;

class WikipediaSearchTest {
  private final String url = "https://www.wikipedia.org/";
  private WebDriver driver;

  @BeforeEach
  void beforeEach() {
    driver = new ChromeDriver();
  }

  @AfterEach
  void afterEach() {
    driver.quit();
  }

  @Test
  void mustSearch() {
    String search = "Selenium (software)";
    HomePage home = new HomePage(driver);
    home.navigate(url).search(search).heading(search);
  }
}
