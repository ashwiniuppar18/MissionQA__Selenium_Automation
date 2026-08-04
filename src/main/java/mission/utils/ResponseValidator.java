package mission.utils;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResponseValidator {

    public void validateStatusCode(Response response, int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus);
    }

    public void validateUser(Response response, String firstName, String email) {

    Assert.assertEquals(response.jsonPath().getString("data.first_name"), firstName);
    Assert.assertEquals(response.jsonPath().getString("data.email"), email);
  }

    public void validateCreatedUser(Response response, String name, String job) {

        Assert.assertEquals(response.jsonPath().getString("name"), name);
        Assert.assertEquals(response.jsonPath().getString("job"), job);

        Assert.assertNotNull(response.jsonPath().getString("id"));
        Assert.assertNotNull(response.jsonPath().getString("createdAt"));
    }


    public void validateErrorMessage(Response response, String expectedMessage) {
        Assert.assertEquals(response.jsonPath().getString("error"), expectedMessage);
    }


    public void validateTotalUsers(Response response, List<Integer> userIds) {
        int totalUsers = response.jsonPath().getInt("total");
        Assert.assertEquals(userIds.size(), totalUsers);
    }

    public void validateUniqueIds(Response response) {
        List<Integer> ids = response.jsonPath().getList("data.id");
        Set<Integer> uniqueIds = new HashSet<>(ids);
        Assert.assertEquals(ids.size(), uniqueIds.size());
    }
}