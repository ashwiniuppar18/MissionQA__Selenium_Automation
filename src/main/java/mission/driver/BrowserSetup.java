package mission.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
                        "C:\\Users\\Ashwini\\Documents\\work_folder\\MissionQaTest\\src\\test\\java\\browserDirectory\\chromedriver.exe"
                );
                driver = new ChromeDriver(createChromeOptions(false));
                break;

            case "chromeheadless":
                System.setProperty(
                        "webdriver.chrome.driver",
                        "C:\\Users\\Ashwini\\Documents\\work_folder\\MissionQaTest\\src\\test\\java\\browserDirectory\\chromedriver.exe"
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
                // No browser required for API tests
                break;

            default:
                Assert.fail(MessageFormat.format("Wrong Browser: {0}", browser));
        }
    }

    private ChromeOptions createChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        File profileDir = new File(System.getProperty("java.io.tmpdir"),
                "selenium-chrome-profile-" + System.currentTimeMillis());
        options.addArguments("--user-data-dir=" + profileDir.getAbsolutePath());

        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromePrefs.put("password_manager_enabled", false);
        chromePrefs.put("offer_to_save_passwords", false);
        chromePrefs.put("save_password_bubble_enabled", false);
        chromePrefs.put("autofill.enabled", false);
        chromePrefs.put("autofill.profile_enabled", false);
        chromePrefs.put("autofill.credit_card_enabled", false);
        chromePrefs.put("profile.default_content_setting_values.notifications", 2);
        chromePrefs.put("profile.default_content_setting_values.popups", 2);
        chromePrefs.put("profile.default_content_setting_values.password_manager", 2);
        chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        options.setExperimentalOption("prefs", chromePrefs);

        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        options.addArguments("--incognito");
        options.addArguments("--password-store=basic");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-password-generation");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-translate");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--no-first-run");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding,AutofillServerCommunication,CredentialManager,PasswordManager,TranslateUI");
        options.addArguments("--disable-blink-features=CredentialManager,AutomationControlled");

        return options;
    }
}