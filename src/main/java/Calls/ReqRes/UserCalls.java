package Calls.ReqRes;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserCalls {

    private final String BASE_URL = "https://reqres.in/api";

    public Response getUsers(int page) {
        return given()
                .queryParam("page", page)
                .when()
                .get(BASE_URL + "/users");
    }
}
