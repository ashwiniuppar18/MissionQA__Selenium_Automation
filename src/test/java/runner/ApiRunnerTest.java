package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
    features = "src/test/resources/API-Test.feature",
    glue = {"steps", "hooks"},
     tags = {"@api"},
    plugin = {
        "pretty",
        "html:target/cucumber-report-api",
        "json:target/cucumber-api.json"
    }
)
public class ApiRunnerTest extends AbstractTestNGCucumberTests {
}