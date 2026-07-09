package com.orangehrm.api;


import com.orangehrm.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.*;
import io.restassured.response.Response;
import io.restassured.specification.*;

import static io.restassured.RestAssured.given;

public class ApiClient {


    private static final String BASE_URL = ConfigReader.get("api.base.url");
    private static RequestSpecification requestSpec;
    private static RequestSpecification authRequestSpec;
    private static ResponseSpecification responseSpec;
    private static String authToken;
    private static boolean isAuthTokenSet = false;
    private static boolean isSpecsBuilt = false;

    private ApiClient() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void buildSpecs() {
        // Build the request specifications for GET and POST requests
        if (isSpecsBuilt) {
            System.out.println("Request and Response specifications are already built. Skipping build.");
            return;
        }


        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
        isSpecsBuilt = true;
    }

    public static void authenticate() {
        if (isAuthTokenSet) {
            System.out.println("Auth token is already set. Skipping authentication.");
            return;
        }

        String requestBody = "{\n" +
                "    \"username\" : \"" + ConfigReader.get("api.username") + "\",\n" +
                "    \"password\" : \"" + ConfigReader.get("api.password") + "\"\n" +
                "}";

        Response response = given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .body(requestBody)
                .when().post("/auth")
                .then().statusCode(200)
                .extract().response();

        authToken = response.jsonPath().getString("token");
        System.out.println("Auth token obtained: " + authToken);

        // Build the authRequestSpec with the obtained authToken
        setAuthToken();
        isAuthTokenSet = true;
    }

    public static void setAuthToken() {
        authRequestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addCookie("token", authToken)
                .build();

        System.out.println("AuthSpec built for PUT,PATCH and DELETE requests");
    }

    public static RequestSpecification getRequestSpec() {
        return given().spec(requestSpec);
    }

  /* public static ResponseSpecification getResponseSpec() {
        return responseSpec;
    }*/

    public static RequestSpecification getAuthRequestSpec() {
        if (!isAuthTokenSet) {
            authenticate();
        }
        return given().spec(authRequestSpec);
    }
}
