package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import mission.driver.BasePage;
import mission.config.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

public class UISteps extends BasePage {

    private String normalizeId(String itemName) {
        return itemName.trim().toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }

    @Given("^I am on the home page$")
    public void i_am_on_the_home_page() {
        String url = ConfigReader.getProperty("url");
        if (url == null || url.isEmpty()) {
            Assert.fail("UI base url is not set in config.properties (key: url)");
        }
        driver.get(url);
    }

    @Given("^I login in with the following details$")
    public void i_login_in_with_the_following_details(DataTable table) {
        String user = null;
        String pass = null;

        List<List<String>> rows = table.asLists(String.class);
        if (rows.size() >= 2) {
            List<String> header = rows.get(0).stream()
                    .map(s -> s.trim().toLowerCase())
                    .collect(Collectors.toList());
            List<String> values = rows.get(1).stream()
                    .map(String::trim)
                    .collect(Collectors.toList());

            int ui = -1, pi = -1;
            for (int i = 0; i < header.size(); i++) {
                String h = header.get(i);
                if (h.equals("username") || h.equals("user") || h.equals("user-name")) {
                    ui = i;
                }
                if (h.equals("password") || h.equals("pass")) {
                    pi = i;
                }
            }
            if (ui != -1 && ui < values.size()) user = values.get(ui);
            if (pi != -1 && pi < values.size()) pass = values.get(pi);
        }

        if (user == null || pass == null) {
            Map<String, String> map = table.asMap(String.class, String.class);
            Map<String, String> norm = map.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey().trim().toLowerCase(), Map.Entry::getValue, (a, b) -> a));

            if (user == null) user = norm.get("username");
            if (user == null) user = norm.get("user");
            if (pass == null) pass = norm.get("password");
            if (pass == null) pass = norm.get("pass");
        }

        if (user == null || pass == null) {
            Assert.fail("Login data missing or keys mismatched. Provided: " + table.asLists(String.class));
        }

        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys(user);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(pass);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();

        wait.until(ExpectedConditions.urlContains("/inventory"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-sauce-labs-backpack")));
    }

    

    @Given("^I add the following items to the basket$")
    public void i_add_the_following_items_to_the_basket(DataTable table) {
        List<String> items = table.asList(String.class);
        WebDriverWait wait = new WebDriverWait(driver, 15);

        for (int index = 0; index < items.size(); index++) {
            String item = items.get(index).trim();
            String addId = "add-to-cart-" + normalizeId(item);
            String removeId = "remove-" + normalizeId(item);

            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(addId)));
            addButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(removeId)));
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("shopping_cart_badge"),
                    String.valueOf(index + 1)));
        }
    }

    @Then("^I  should see (\\d+) items added to the shopping cart$")
    public void i_should_see_items_added_to_the_shopping_cart(int expectedCount) {
        WebElement badge = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));
        int count = Integer.parseInt(badge.getText().trim());
        Assert.assertEquals(count, expectedCount);
    }

    @Given("^I click on the shopping cart$")
    public void i_click_on_the_shopping_cart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @Then("^I verify that the QTY count for each item should be (\\d+)$")
    public void i_verify_that_the_QTY_count_for_each_item_should_be(int expectedQty) {
        List<WebElement> qtyElements = driver.findElements(By.className("cart_quantity"));
        for (WebElement qty : qtyElements) {
            Assert.assertEquals(Integer.parseInt(qty.getText()), expectedQty);
        }
    }

    @Given("^I remove the following item:$")
    public void i_remove_the_following_item(DataTable table) {
        List<String> items = table.asList(String.class);
        for (String item : items) {
            String id = "remove-" + normalizeId(item);
            driver.findElement(By.id(id)).click();
        }
    }

    @Given("^I click on the CHECKOUT button$")
    public void i_click_on_the_CHECKOUT_button() {
        driver.findElement(By.id("checkout")).click();
    }

    @Given("^I type \"([^\"]*)\" for First Name$")
    public void i_type_for_First_Name(String firstName) {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
    }

    @Given("^I type \"([^\"]*)\" for Last Name$")
    public void i_type_for_Last_Name(String lastName) {
        driver.findElement(By.id("last-name")).sendKeys(lastName);
    }

    @Given("^I type \"([^\"]*)\" for ZIP/Postal Code$")
    public void i_type_for_ZIP_Postal_Code(String zip) {
        driver.findElement(By.id("postal-code")).sendKeys(zip);
    }

    @When("^I click on the CONTINUE button$")
    public void i_click_on_the_CONTINUE_button() {
        driver.findElement(By.id("continue")).click();
    }

    @Then("^Item total will be equal to the total of items on the list$")
    public void item_total_will_be_equal_to_the_total_of_items_on_the_list() {
        List<WebElement> priceEls = driver.findElements(By.className("inventory_item_price"));
        double sum = priceEls.stream()
                .map(WebElement::getText)
                .map(s -> s.replace("$", "").trim())
                .mapToDouble(Double::parseDouble)
                .sum();

        String subtotalText = driver.findElement(By.className("summary_subtotal_label")).getText();
        double displayed = Double.parseDouble(subtotalText.replaceAll("[^0-9.]", ""));

        Assert.assertEquals(displayed, Math.round(sum * 100.0) / 100.0, "Item subtotal mismatch");
    }

    @Then("^a Tax rate of (\\d+) % is applied to the total$")
    public void a_Tax_rate_of_percent_is_applied_to_the_total(int percent) {
        String subtotalText = driver.findElement(By.className("summary_subtotal_label")).getText();
        double subtotal = Double.parseDouble(subtotalText.replaceAll("[^0-9.]", ""));

        String taxText = driver.findElement(By.className("summary_tax_label")).getText();
        double taxDisplayed = Double.parseDouble(taxText.replaceAll("[^0-9.]", ""));

        double expectedTax = Math.round(subtotal * percent / 100.0 * 100.0) / 100.0;
        Assert.assertEquals(taxDisplayed, expectedTax, "Tax calculation mismatch");
    }
}