package api.base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseApi {

    protected static final String BASE_URL = "https://api.trello.com/1";

    protected String getKey() {
        String key = System.getenv("TRELLO_KEY");
        if (key == null || key.isBlank()) {
            throw new RuntimeException("❌ TRELLO_KEY environment variable bulunamadı");
        }
        return key;
    }

    protected String getToken() {
        String token = System.getenv("TRELLO_TOKEN");
        if (token == null || token.isBlank()) {
            throw new RuntimeException("❌ TRELLO_TOKEN environment variable bulunamadı");
        }
        return token;
    }

    protected RequestSpecification request() {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .queryParam("key", getKey())
                .queryParam("token", getToken())
                .header("Accept", "application/json");
    }
}
