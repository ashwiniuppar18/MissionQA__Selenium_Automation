package mission.pages;
import mission.driver.BasePage;
import mission.config.ConfigReader;

public class HomePage extends BasePage {

    public static void homePage() {
        driver.get(ConfigReader.getProperty("url"));
    }
}
