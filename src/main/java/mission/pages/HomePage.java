package mission.pages;
import mission.config.LoadProp;
import mission.driver.BasePage;
public class HomePage extends BasePage {

    public static void homePage() {
        driver.get(LoadProp.getProperty("url"));
    }
}
