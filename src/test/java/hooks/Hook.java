package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import mission.config.ConfigReader;
import mission.config.IniClass;
import mission.driver.BasePage;
import mission.driver.BrowserSetup;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Hook extends BasePage {

    private static final int WAIT_SEC = 20;
    private final BrowserSetup browserSetup = new BrowserSetup();

    @Before
    public void initializeTest() {
        String browser = ConfigReader.getProperty("Browser");

        if ("api".equalsIgnoreCase(browser)) {
            IniClass.initialize();
            return;
        }

        browserSetup.selectBrowser();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(WAIT_SEC, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(WAIT_SEC, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(WAIT_SEC, TimeUnit.SECONDS);
        IniClass.initialize();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String fileName = scenario.getName().replace(" ", "")
                        + new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "")
                        + "_" + ConfigReader.getProperty("Browser") + ".jpg";
                FileUtils.copyFile(screenshot, new File(ConfigReader.getProperty("ScreenshotLocation") + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}