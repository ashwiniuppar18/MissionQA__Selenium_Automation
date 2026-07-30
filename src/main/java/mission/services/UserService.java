package mission.services;

import io.restassured.response.Response;
import mission.api.ApiClient;
import mission.models.CreateUserRequest;

public class UserService {

    private ApiClient apiClient = new ApiClient();

    // Get single user
    public Response getUser(int userId) {
        return apiClient.get("/users/" + userId);
    }

    // Get users by page
    public Response getUsers(int page) {
        return apiClient.get("/users", "page", page);
    }

    // Delayed response
    public Response getDelayedUsers(int delay) {
        return apiClient.get("/users", "delay", delay);
    }

    // Create new user
    public Response createUser(String name, String job) {

        CreateUserRequest request = new CreateUserRequest(name, job);

        return apiClient.post("/users", request);
    }

}