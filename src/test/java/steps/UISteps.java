package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import mission.driver.BasePage;
import mission.pages.CartPage;
import mission.pages.CheckoutPage;
import mission.pages.InventoryPage;

public class UISteps extends BasePage {

    private final InventoryPage inventoryPage = new InventoryPage();
    private final CartPage cartPage = new CartPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    @Given("^I am on the home page$")
    public void i_am_on_the_home_page() {
        inventoryPage.open();
    }

    @Given("^I login in with the following details$")
    public void i_login_in_with_the_following_details(DataTable table) {
        String username = table.asMaps(String.class, String.class).get(0).get("username");
        String password = table.asMaps(String.class, String.class).get(0).get("password");
        inventoryPage.login(username, password);
    }

    @Given("^I add the following items to the basket$")
    public void i_add_the_following_items_to_the_basket(DataTable table) {
        inventoryPage.addItems(table.asList(String.class));
    }

    @Then("^I  should see (\\d+) items added to the shopping cart$")
    public void i_should_see_items_added_to_the_shopping_cart(int expectedCount) {
        inventoryPage.assertCartCount(expectedCount);
    }

    @Given("^I click on the shopping cart$")
    public void i_click_on_the_shopping_cart() {
        cartPage.open();
    }

    @Then("^I verify that the QTY count for each item should be (\\d+)$")
    public void i_verify_that_the_QTY_count_for_each_item_should_be(int expectedQty) {
        cartPage.assertQty(expectedQty);
    }

    @Given("^I remove the following item:$")
    public void i_remove_the_following_item(DataTable table) {
        cartPage.removeItem(table.asList(String.class).get(0));
    }

    @Given("^I click on the CHECKOUT button$")
    public void i_click_on_the_CHECKOUT_button() {
        checkoutPage.startCheckout();
    }

@Given("^I type \"([^\"]*)\" for First Name$")
public void i_type_for_First_Name(String firstName) {
    checkoutPage.enterValue("first-name", firstName);
}

@Given("^I type \"([^\"]*)\" for Last Name$")
public void i_type_for_Last_Name(String lastName) {
    checkoutPage.enterValue("last-name", lastName);
}

@Given("^I type \"([^\"]*)\" for ZIP/Postal Code$")
public void i_type_for_ZIP_Postal_Code(String zip) {
    checkoutPage.enterValue("postal-code", zip);
}

    @When("^I click on the CONTINUE button$")
    public void i_click_on_the_CONTINUE_button() {
        checkoutPage.continueCheckout();
    }

    @Then("^Item total will be equal to the total of items on the list$")
    public void item_total_will_be_equal_to_the_total_of_items_on_the_list() {
        checkoutPage.assertSubtotalMatchesItems();
    }

    @Then("^a Tax rate of (\\d+) % is applied to the total$")
    public void a_Tax_rate_of_percent_is_applied_to_the_total(int percent) {
        checkoutPage.assertTax(percent);
    }
}