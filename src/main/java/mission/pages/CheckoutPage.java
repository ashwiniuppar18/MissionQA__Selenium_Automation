package mission.pages;

import mission.driver.BasePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.openqa.selenium.WebElement;

public class CheckoutPage extends BasePage {

    public void startCheckout() {
        driver.findElement(By.id("checkout")).click();
    }

    public void enterValue(String fieldId, String value) {
        driver.findElement(By.id(fieldId)).sendKeys(value);
    }

    public void continueCheckout() {
        driver.findElement(By.id("continue")).click();
    }

    public void assertSubtotalMatchesItems() {
        double subtotal = getNumber("summary_subtotal_label");
        double total = getItemTotal();
        Assert.assertEquals(subtotal, total, "Subtotal does not match item total");
    }

    public void assertTax(int percent) {
        double subtotal = getNumber("summary_subtotal_label");
        double tax = getNumber("summary_tax_label");
      double expectedTax = Math.round(subtotal * percent / 100.0 * 100.0) / 100.0;
      double actualTax = Math.round(tax * 100.0) / 100.0;

     Assert.assertEquals(actualTax, expectedTax, "Tax is incorrect");
    }

    private double getNumber(String className) {
        String text = driver.findElement(By.className(className)).getText();
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    private double getItemTotal() {
        double total = 0;
       for (WebElement item : driver.findElements(By.className("inventory_item_price")))  {
            total += Double.parseDouble(item.getText().replace("$", "").trim());
        }
        return Math.round(total * 100.0) / 100.0;
    }
}