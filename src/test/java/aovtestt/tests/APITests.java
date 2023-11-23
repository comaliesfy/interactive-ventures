package aovtestt.tests;

import aovtestt.requests.Requests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class APITests {

    String gender = "male";
    String userId = "10";

    @BeforeClass
    public static void start() {
        RestAssured.baseURI = "http://hr-challenge.interactive-ventures.com";
    }

    @Test
    public void testGetUsersInfoCheck() {
        Response response = Requests.getAllUsers(gender);

        assertEquals(200, response.getStatusCode());
        assertEquals( "application/json;charset=UTF-8",response.getContentType());

        assertTrue(response.body().jsonPath().getBoolean("isSuccess"));
        assertTrue(response.body().jsonPath().get("isSuccess") instanceof Boolean);
        assertTrue(response.body().jsonPath().get("errorCode") instanceof Integer);
        assertNotNull(response.body().jsonPath().getList("idList"));
        assertTrue(response.body().jsonPath().getList("idList") instanceof ArrayList);
    }

    @Test
    public void testGetOneUserInfoCheck() {
        Response response = Requests.getUserByID(userId);

        assertEquals(200, response.getStatusCode());

        assertTrue(response.getContentType().startsWith("application/json"));
        assertTrue(response.body().jsonPath().get("isSuccess") instanceof Boolean);
        assertTrue(response.body().jsonPath().get("errorCode") instanceof Integer);
        assertTrue(response.body().jsonPath().get("user") instanceof java.util.Map);
    }

    @Test
    public void testCheckUsersMaleExist() {

        Response usersResponse = Requests.getAllUsers(gender);
        assertEquals(200, usersResponse.getStatusCode());
        List<Integer> idList = usersResponse.jsonPath().getList("idList");
        System.out.println(idList);
        assertFalse(idList.isEmpty());

        for (Integer userId : idList) {
            Response userByIdApiResponse = Requests.getUserByID(userId.toString());


            // Assertions for each user response
            assertEquals(200, userByIdApiResponse.getStatusCode());
            assertEquals(userId, userByIdApiResponse.getBody().jsonPath().get("user.id"));
        }
    }


    @Test
    public void testCheckUsersFemaleExist() {

        Response usersResponse = Requests.getAllUsers("female");
        assertEquals(200, usersResponse.getStatusCode());
        List<Integer> idList = usersResponse.jsonPath().getList("idList");
        System.out.println(idList);
        assertFalse(idList.isEmpty());

        for (Integer userId : idList) {
            Response userByIdApiResponse = Requests.getUserByID(userId.toString());


            // Assertions for each user response
            assertEquals(200, userByIdApiResponse.getStatusCode());
            assertEquals(userId, userByIdApiResponse.getBody().jsonPath().get("user.id"));
        }
    }
}
