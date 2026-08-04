package mission.pages;

import mission.driver.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class CartPage extends BasePage {

    public void open() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    public void assertQty(int expectedQty) {
        List<WebElement> qtyElements = driver.findElements(By.className("cart_quantity"));
        for (WebElement qty : qtyElements) {
            Assert.assertEquals(Integer.parseInt(qty.getText()), expectedQty);
        }
    }

    public void removeItem(String item) {
        driver.findElement(By.id("remove-" + normalize(item))).click();
    }

    private String normalize(String item) {
        return item.trim().toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
}