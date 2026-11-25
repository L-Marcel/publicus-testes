package ufrn.imd.pages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ufrn.imd.abstracts.GenericPage;

public class CreateVisitPage extends GenericPage<CreateVisitPage> {
  private static final Pattern TIME_PATTERN = Pattern.compile("\\b(\\d{2}:\\d{2})\\b");
  private String visitTime;

  public CreateVisitPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected CreateVisitPage self() {
    return this;
  }

  public CreateVisitPage singleVisit() {
    this.buttonByText("Visita avulsa").click();
    logger.info("Selecionada visita avulsa");
    return this;
  }

  public CreateVisitPage groupVisit() {
    this.buttonByText("Visita em grupo").click();
    logger.info("Selecionada visita em grupo");
    return this;
  }

  public CreateVisitPage visitors(int count) {
    this.replaceText(
      this.inputByLabel("N.º de visitantes"),
      String.valueOf(count)
    );
    logger.info("Preenchido número de visitantes: " + count);
    return this;
  }

  public CreateVisitPage childrenOrTeens(int count) {
    this.replaceText(
      this.inputByLabel("N.º de crianças ou adolescentes"),
      String.valueOf(count)
    );
    logger.info("Preenchido número de crianças ou adolescentes: " + count);
    return this;
  }

  public CreateVisitPage specialNeedsVisitors(int count) {
    this.replaceText(
      this.inputByLabel("N.º de visitantes com NE"),
      String.valueOf(count)
    );
    logger.info("Preenchido número de visitantes com NE: " + count);
    return this;
  }

  public CreateVisitPage specialNeedsTypes(String types) {
    this.replaceText(
      this.inputByLabel("Tipos de necessidades específicas"),
      types
    );
    logger.info("Preenchidos tipos de necessidades específicas");
    return this;
  }

  public CreateVisitPage accessibilityResources(String resources) {
    this.replaceText(
      this.textareaByLabel("Recursos de acessibilidade necessários"),
      resources
    );
    logger.info("Preenchidos recursos de acessibilidade necessários");
    return this;
  }

  public CreateVisitPage notes(String notes) {
    this.replaceText(
      this.textareaByLabel("Observações"),
      notes
    );
    logger.info("Preenchidas observações da visita");
    return this;
  }

  public CreateVisitPage manualDateTime(String date, String time) {
    this.buttonByText("definição manual").click();
    this.replaceText(this.inputByLabel("Data da visita"), date);
    this.replaceText(this.inputByLabel("Horário da visita"), time);
    this.visitTime = time;
    logger.info("Preenchidos data e horário manuais: " + date + " " + time);
    return this;
  }

  private String captureAutomaticTime() {
    WebElement dateTimeElement = this.until(
      "data e hora automática da visita não encontradas",
      ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//flt-semantics-host//span[contains(normalize-space(), '/') and contains(normalize-space(), ':')]")
      )
    );

    Matcher matcher = TIME_PATTERN.matcher(dateTimeElement.getAttribute("textContent"));
    return matcher.find() ? matcher.group(1) : "";
  }

  public CreateVisitPage submit() {
    if (this.visitTime == null || this.visitTime.isBlank())
      this.visitTime = this.captureAutomaticTime();
    this.buttonByText("Cadastrar visita").click();
    logger.info("Cadastro de visita enviado (horário: " + this.visitTime + ")");
    return this;
  }

  public CreateVisitPage recentVisitCard(int totalVisitors) {
    return this.recentVisitCard(this.visitTime, totalVisitors);
  }

  public CreateVisitPage recentVisitCard(String expectedTime, int totalVisitors) {
    String visitorsLabel = totalVisitors == 1 ? "1 visitante" : totalVisitors + " visitantes";
    String timeLabel = expectedTime + " h";

    this.until(
      "cartão de visita recente \"" + timeLabel + " • " + visitorsLabel + "\" não encontrado",
      ExpectedConditions.visibilityOfElementLocated(
        By.xpath(
          "//flt-semantics-host//*[self::flt-semantics and not(.//form)" +
            " and contains(normalize-space(), '" + timeLabel + "')" +
            " and contains(normalize-space(), '" + visitorsLabel + "')]"
        )
      )
    );

    logger.info("Cartão de visita recente verificado \"" + timeLabel + " • " + visitorsLabel + "\"");
    return this;
  }

  public HomePage cancel() {
    this.buttonByText("Cancelar").click();
    logger.info("Cadastro de visita cancelado para voltar ao início");
    this.buttonByText("Solicitar agendamento de visita");
    return new HomePage(driver);
  }
}
