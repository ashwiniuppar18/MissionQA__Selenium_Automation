package mission.services;

import io.restassured.response.Response;
import mission.api.ApiClient;
import mission.models.LoginRequest;

public class LoginService {

    private ApiClient apiClient;

    public LoginService() {

        apiClient = new ApiClient();

    }

    /**
     * POST /login
     */
    public Response login(String email, String password) {

        LoginRequest request =
                new LoginRequest(email, password);

        return apiClient.post("/login", request);

    }

}