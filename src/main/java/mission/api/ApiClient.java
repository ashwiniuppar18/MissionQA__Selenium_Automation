package mission.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mission.config.LoadProp;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private final String apiKey;

    public ApiClient() {
        RestAssured.baseURI = LoadProp.getBaseUrl();
        apiKey = LoadProp.getProperty("api.key");
    }

    private io.restassured.specification.RequestSpecification request() {
        return (apiKey != null && !apiKey.trim().isEmpty())
                ? given().accept(ContentType.JSON).header("x-api-key", apiKey)
                : given().accept(ContentType.JSON);
    }

    public Response get(String endpoint) {
        return request().when().get(endpoint);
    }

    public Response get(String endpoint, String param, Object value) {
        return request().queryParam(param, value).when().get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return request().contentType(ContentType.JSON).body(body).when().post(endpoint);
    }

    public Response put(String endpoint, Object body) {
        return request().contentType(ContentType.JSON).body(body).when().put(endpoint);
    }

    public Response delete(String endpoint) {
        return request().when().delete(endpoint);
    }
}