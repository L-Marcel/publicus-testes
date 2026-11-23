package ufrn.imd;

import org.junit.jupiter.api.Test;
import ufrn.imd.abstracts.GenericWebTest;
import ufrn.imd.pages.HomePage;

class WikipediaSearchTest extends GenericWebTest {
  private final String url = "https://www.wikipedia.org/";

  @Test
  void mustSearch() {
    String search = "Selenium (software)";
    HomePage home = new HomePage(driver);
    home.navigate(url).search(search).heading(search);
  }
}
