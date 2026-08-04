package mission.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.io.File;
import java.text.MessageFormat;

import mission.config.ConfigReader;

public class BrowserSetup extends BasePage {

    public static String browser = null;

    public void selectBrowser() {
        browser = ConfigReader.getProperty("Browser");

        if (browser == null || browser.trim().isEmpty()) {
            Assert.fail("Browser property is not set");
        }

        switch (browser.toLowerCase()) {
            case "chrome":
            case "chromemac":
                System.setProperty(
                        "webdriver.chrome.driver",
                        "src\\test\\java\\BrowserDirectory\\chromedriver.exe"
                );
        driver = new ChromeDriver(createChromeOptions(false));
        break;

            case "chromeheadless":
                System.setProperty(
                        "webdriver.chrome.driver",
                        "src\\test\\java\\BrowserDirectory\\chromedriver.exe"
                );
                driver = new ChromeDriver(createChromeOptions(true));
                break;

            case "edge":
                driver = new EdgeDriver();
                break;

            case "firefox":
                driver = new FirefoxDriver();
                break;

            case "api":
                break;

            default:
                Assert.fail(MessageFormat.format("Wrong Browser: {0}", browser));
        }
    }

    private ChromeOptions createChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions(); 

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        options.addArguments("--incognito");
        return options;
    }
}