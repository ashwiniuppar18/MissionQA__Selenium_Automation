package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import mission.driver.BasePage;
import org.openqa.selenium.NoSuchSessionException;
import org.testng.annotations.AfterClass;

@CucumberOptions(
    features = "src/test/resources/UI-Test.feature",
    glue = {"steps", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-report-ui",
        "json:target/cucumber-ui.json"
    }
)
public class UiRunnerTest extends AbstractTestNGCucumberTests {

    @AfterClass(alwaysRun = true)
    public void closeBrowserAfterAllScenarios() {
        if (BasePage.driver != null) {
            BasePage.driver.quit();
            BasePage.driver = null;
        }
    }
}