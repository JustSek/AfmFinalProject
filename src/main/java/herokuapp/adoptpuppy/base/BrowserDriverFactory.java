package herokuapp.adoptpuppy.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserDriverFactory {


//    protected WebDriver driver;
    private String browser;
    private Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

        public WebDriver create_Driver() {
            log.info("Create driver: " + browser);
            WebDriver driver;
            switch (browser) {
                case "chrome":
                    //WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(new FirefoxOptions().setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe"));
                    break;

                default:
                    System.out.println("Do not know how to start: " + browser + ", starting chrome.");
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
            }

            return driver;
        }
}
