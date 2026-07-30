package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import mission.driver.BrowserSetup;
import mission.driver.BasePage;
import mission.config.ConfigReader;

import mission.config.IniClass;

public class Hook extends BasePage {

    BrowserSetup browsersetup = new BrowserSetup();

    private static final int WAIT_SEC = 20;

    @Before()
    public void initializeTest() {
        String browser = ConfigReader.getProperty("Browser");

        if ("api".equalsIgnoreCase(browser)) {
            IniClass.initialize();
            return;
        }

        browsersetup.selectBrowser();

        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().pageLoadTimeout(WAIT_SEC, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(WAIT_SEC, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(WAIT_SEC, TimeUnit.SECONDS);
        }

        IniClass.initialize();
    }

    /**
     * Executed after each UI tagged scenario
     */
    @After()
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenShotFilename = scenario.getName().replace(" ", "")
                    + new Timestamp(new Date().getTime()).toString().replaceAll("[^a-zA-Z0-9]", "")
                    + "_" + ConfigReader.getProperty("Browser") + ".jpg";
            File scrFile = null;
            if (driver != null) {
                try {
                    scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                } catch (WebDriverException e) {
                    scrFile = null;
                }
            }

            try {
                if (scrFile != null) {
                    FileUtils.copyFile(scrFile, new File(ConfigReader.getProperty("ScreenshotLocation") + screenShotFilename));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Do not quit the browser here if you want it to stay open across scenarios.
    }
}