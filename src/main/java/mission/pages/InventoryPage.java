package mission.pages;

import mission.driver.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import mission.config.LoadProp;

public class InventoryPage extends BasePage {

    private final int TIMEOUT = 20;

    public void open() {
        String url = LoadProp.getProperty("url");
        if (url == null || url.isEmpty()) {
            Assert.fail("URL is missing in config.properties");
        }
        driver.get(url);
    }

    public void login(String username, String password) {
        waitFor(By.id("user-name")).sendKeys(username);
        waitFor(By.id("password")).sendKeys(password);
        waitFor(By.id("login-button")).click();
        waitFor(By.className("inventory_list"));
    }

    public void addItems(List<String> items) {
        for (String item : items) {
            String id = "add-to-cart-" + toId(item);
            driver.findElement(By.id(id)).click();
        }
    }

    public void assertCartCount(int expected) {
        String text = waitFor(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(Integer.parseInt(text), expected);
    }

    private WebElement waitFor(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private String toId(String itemName) {
        return itemName.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
}