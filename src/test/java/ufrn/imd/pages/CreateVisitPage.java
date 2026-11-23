package ufrn.imd.pages;

import org.openqa.selenium.WebDriver;
import ufrn.imd.abstracts.GenericPage;

public class CreateVisitPage extends GenericPage<CreateVisitPage> {
  public CreateVisitPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected CreateVisitPage self() {
    return this;
  }
}
