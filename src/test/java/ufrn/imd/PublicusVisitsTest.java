package ufrn.imd;

import org.junit.jupiter.api.Test;
import ufrn.imd.abstracts.GenericWebTest;
import ufrn.imd.pages.LoginPage;

public class PublicusVisitsTest extends GenericWebTest {
  private final String url = "https://testeihc.imd.ufrn.br/publicus/";

  @Test
  void mustCreateVisitAsRecepcionist() {
    LoginPage home = new LoginPage(driver);

    home
      .navigate(url)
      .login("r", "r")
      .menus(
        "Início",
        "Agendamentos",
        "Visitas",
        "Responsáveis",
        "Relatórios"
      )
      .createVisit()
      .singleVisit()
      .visitors(5)
      .childrenOrTeens(2)
      .specialNeedsVisitors(1)
      .specialNeedsTypes("Auditiva")
      .accessibilityResources("Intérprete de Libras")
      .notes("Visita cadastrada via teste automatizado (recepcionista)")
      .submit()
      .menus(
        "Início",
        "Agendamentos",
        "Visitas",
        "Responsáveis",
        "Relatórios"
      );
  }

  @Test
  void mustCreateVisitAsAdmin() {
    LoginPage home = new LoginPage(driver);

    home
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
      )
      .createVisit()
      .singleVisit()
      .visitors(3)
      .childrenOrTeens(1)
      .notes("Visita cadastrada via teste automatizado (administrador)")
      .submit()
      .menus(
        "Início",
        "Agendamentos",
        "Visitas",
        "Responsáveis",
        "Relatórios",
        "Configurações",
        "Colaboradores"
      );
  }
}
