package mission.api;

import mission.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private final String apiKey;

    public ApiClient() {
        RestAssured.baseURI = ConfigReader.getBaseUrl();
        apiKey = ConfigReader.getProperty("api.key");
    }

    private io.restassured.specification.RequestSpecification withKey() {
        if (apiKey != null && !apiKey.trim().isEmpty()) {
            return given().accept(ContentType.JSON).header("x-api-key", apiKey);
        } else {
            return given().accept(ContentType.JSON);
        }
    }

    public Response get(String endpoint) {
        Response r = withKey().when().get(endpoint);
        System.out.println("GET " + endpoint + " -> " + r.getStatusCode() + " " + r.asString());
        return r;
    }

    public Response get(String endpoint, String queryParam, Object value) {
        Response r = withKey().queryParam(queryParam, value).when().get(endpoint);
        System.out.println("GET " + endpoint + " (?" + queryParam + "=" + value + ") -> " + r.getStatusCode() + " " + r.asString());
        return r;
    }

    public Response post(String endpoint, Object body) {
        Response r = withKey().contentType(ContentType.JSON).body(body).when().post(endpoint);
        System.out.println("POST " + endpoint + " -> " + r.getStatusCode() + " " + r.asString());
        return r;
    }

    public Response put(String endpoint, Object body) {
        Response r = withKey().contentType(ContentType.JSON).body(body).when().put(endpoint);
        System.out.println("PUT " + endpoint + " -> " + r.getStatusCode() + " " + r.asString());
        return r;
    }

    public Response delete(String endpoint) {
        Response r = withKey().when().delete(endpoint);
        System.out.println("DELETE " + endpoint + " -> " + r.getStatusCode() + " " + r.asString());
        return r;
    }
}