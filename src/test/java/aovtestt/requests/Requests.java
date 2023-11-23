package aovtestt.requests;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Requests {

    public static Response getAllUsers(String gender){
        Response response = given()
                .param("gender", gender)
                .header("accept", "application/json")
                .when()
                .get("/api/test/users");
        return response;
    }

    public static Response getUserByID(String userId){
        Response response = given()
                .pathParam("id", userId)
                .when()
                .get("/api/test/user/{id}");
        return response;
    }
}
