package ufrn.imd.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver extends ChromeDriver {
  public Driver() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--force-renderer-accessibility");
    super(options);
  }
}
