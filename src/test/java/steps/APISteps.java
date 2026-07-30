package steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.restassured.response.Response;

import mission.services.LoginService;
import mission.services.UserService;
import mission.utils.ResponseValidator;
import org.testng.Assert;

public class APISteps {

    // Service classes to call API methods
    UserService userService = new UserService();
    LoginService loginService = new LoginService();

    // Validation class
    ResponseValidator validator = new ResponseValidator();

    // Stores latest API response
    Response response;

    // Stores all user IDs from all pages
    List<Integer> allUserIds = new ArrayList<>();
   String createdUserName;
   String createdUserJob;

    //=========================================================
    // LIST USERS
    //=========================================================

    @Given("^I get the default list of users for on (\\d+)st page$")
    public void i_get_the_default_list_of_users_for_on_st_page(int page) {

        // Get users from the given page
        response = userService.getUsers(page);
        validator.validateStatusCode(response, 200);
    }

    @When("^I get the list of all users within every page$")
    public void i_get_the_list_of_all_users_within_every_page() {

        Assert.assertNotNull(response, "Initial response must not be null");
        validator.validateStatusCode(response, 200);

        // Find how many pages are available
        int totalPages = response.jsonPath().getInt("total_pages");

        // Remove old IDs if any
        allUserIds.clear();

        // Loop through every page
        for (int i = 1; i <= totalPages; i++) {

            Response pageResponse = userService.getUsers(i);
            Assert.assertNotNull(pageResponse, "pageResponse must not be null for page " + i);
            validator.validateStatusCode(pageResponse, 200);

            // Get all IDs from current page
            List<Integer> ids = pageResponse.jsonPath().getList("data.id");

            // Add IDs to one common list
            allUserIds.addAll(ids);
        }
    }

    @Then("^I should see total users count equals the number of user ids$")
    public void i_should_see_total_users_count_equals_the_number_of_user_ids() {

        // Compare total users with collected IDs
        validator.validateTotalUsers(response, allUserIds);
    }


    //=========================================================
    // SINGLE USER
    //=========================================================

    @Given("^I make a search for user (\\d+)$")
    public void i_make_a_search_for_user(int userId) {

        // Search for one user
        response = userService.getUser(userId);
    }

  @Then("^I should see the following user data$")
  public void i_should_see_the_following_user_data(DataTable table) {

    Assert.assertNotNull(response, "Response must not be null");
    validator.validateStatusCode(response, 200);

    List<List<String>> data = table.raw();

    validator.validateUser(
            response,
            data.get(1).get(0),
            data.get(1).get(1));
  }


    //=========================================================
    // USER NOT FOUND
    //=========================================================

    @Then("^I receive error code (\\d+) in response$")
    public void i_receive_error_code_in_response(int statusCode) {

        validator.validateStatusCode(response, statusCode);
    }


    //=========================================================
    // CREATE USER
    //=========================================================
  @Given("^I create a user with following (.+) (.+)$")
  public void i_create_a_user_with_following(String name, String job) {

    createdUserName = name;
    createdUserJob = job;

    response = userService.createUser(name, job);
    validator.validateStatusCode(response, 201);
  }

    @Then("^response should contain the following data$")
   public void response_should_contain_the_following_data(DataTable table) {

    validator.validateStatusCode(response,201);

    validator.validateCreatedUser(
            response,
            createdUserName,
            createdUserJob);
   }


    //=========================================================
    // LOGIN
    //=========================================================

    @Given("^I login unsuccessfully with the following data$")
    public void i_login_unsuccessfully_with_the_following_data(DataTable table) {

        // Read login details from feature file
        Map<String, String> data =
                table.asMap(String.class, String.class);

        // Call login API
        response = loginService.login(
            data.get("Email"),
            data.get("Password"));
    }

    @Then("^I should get a response code of (\\d+)$")
    public void i_should_get_a_response_code_of(int statusCode) {

        validator.validateStatusCode(response, statusCode);
    }

    @Then("^I should see the following response message:$")
    public void i_should_see_the_following_response_message(DataTable table) {

        Map<String, String> data =
                table.asMap(String.class, String.class);

        validator.validateErrorMessage(
                response,
                data.get("error"));
    }


    //=========================================================
    // DELAY RESPONSE
    //=========================================================

    @Given("^I wait for the user list to load$")
    public void i_wait_for_the_user_list_to_load() {

        // Call delayed response API
       response = userService.getDelayedUsers(3);
       validator.validateStatusCode(response, 200);
    }

    @Then("^I should see that every user has a unique id$")
    public void i_should_see_that_every_user_has_a_unique_id() {

        // Check API returned success
        validator.validateStatusCode(response, 200);

        // Check IDs are unique
        validator.validateUniqueIds(response);
    }
}