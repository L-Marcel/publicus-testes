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

  public CreateVisitPage singleVisit() {
    this.buttonByText("Visita avulsa").click();
    logger.info("Selected single visit");
    return this;
  }

  public CreateVisitPage groupVisit() {
    this.buttonByText("Visita em grupo").click();
    logger.info("Selected group visit");
    return this;
  }

  public CreateVisitPage visitors(int count) {
    this.replaceText(
      this.inputByLabel("N.º de visitantes"),
      String.valueOf(count)
    );
    logger.info("Filled visitors count: " + count);
    return this;
  }

  public CreateVisitPage childrenOrTeens(int count) {
    this.replaceText(
      this.inputByLabel("N.º de crianças ou adolescentes"),
      String.valueOf(count)
    );
    logger.info("Filled children or teens count: " + count);
    return this;
  }

  public CreateVisitPage specialNeedsVisitors(int count) {
    this.replaceText(
      this.inputByLabel("N.º de visitantes com NE"),
      String.valueOf(count)
    );
    logger.info("Filled special needs visitors count: " + count);
    return this;
  }

  public CreateVisitPage specialNeedsTypes(String types) {
    this.replaceText(
      this.inputByLabel("Tipos de necessidades específicas"),
      types
    );
    logger.info("Filled special needs types");
    return this;
  }

  public CreateVisitPage accessibilityResources(String resources) {
    this.replaceText(
      this.textareaByLabel("Recursos de acessibilidade necessários"),
      resources
    );
    logger.info("Filled accessibility resources");
    return this;
  }

  public CreateVisitPage notes(String notes) {
    this.replaceText(this.textareaByLabel("Observações"), notes);
    logger.info("Filled visit notes");
    return this;
  }

  public CreateVisitPage manualDateTime(String date, String time) {
    this.buttonByText("definição manual").click();
    this.replaceText(this.inputByLabel("Data da visita"), date);
    this.replaceText(this.inputByLabel("Horário da visita"), time);
    logger.info("Filled manual date and time: " + date + " " + time);
    return this;
  }

  public CreateVisitPage submit() {
    this.buttonByText("Cadastrar visita").click();
    logger.info("Submitted visit creation");
    return this;
  }

  public CreateVisitPage recentVisitCard(int totalVisitors) {
    String label = totalVisitors == 1 ? "1 visitante" : totalVisitors + " visitantes";
    this.textContaining(label);
    logger.info("Verified recent visit card with \"" + label + "\"");
    return this;
  }

  public HomePage cancel() {
    this.buttonByText("Cancelar").click();
    logger.info("Cancelled visit creation to return home");
    this.buttonByText("Solicitar agendamento de visita");
    return new HomePage(driver);
  }
}
