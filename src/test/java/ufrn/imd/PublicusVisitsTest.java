package ufrn.imd;

import org.junit.jupiter.api.Test;
import ufrn.imd.abstracts.GenericWebTest;
import ufrn.imd.pages.CreateVisitPage;
import ufrn.imd.pages.LoginPage;

public class PublicusVisitsTest extends GenericWebTest {
  private final String url = "https://testeihc.imd.ufrn.br/publicus/";

  @Test
  void mustCreateVisitAsRecepcionist() {
    LoginPage home = new LoginPage(driver);

    CreateVisitPage createVisit = home
      .navigate(url)
      .login("r", "r")
      .menus(
        "Início",
        "Agendamentos",
        "Visitas"
      ).createVisit();
  }

  @Test
  void mustCreateVisitAsAdmin() {
    LoginPage home = new LoginPage(driver);

    CreateVisitPage createVisit = home
      .navigate(url)
      .login("administrador", "publicus")
      .menus(
        "Início",
        "Agendamentos",
        "Visitas",
        "Responsáveis",
        "Relatórios",
        "Configurações",
        "Colaboradores"
      ).createVisit();
  }
}
