package ufrn.imd.pages;

import org.openqa.selenium.WebDriver;
import ufrn.imd.abstracts.GenericPageWithMenu;

public class HomePage extends GenericPageWithMenu<HomePage> {
  public HomePage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected HomePage self() {
    return this;
  }

  public CreateVisitPage createVisit() {
    this.buttonByText("Cadastrar visita").click();
    logger.info("Creating a new visit");
    return new CreateVisitPage(driver);
  }
}
