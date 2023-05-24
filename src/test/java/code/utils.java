package code;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class utils {
    public static String getBookId(){
        Response response= RestAssured.get("https://simple-books-api.glitch.me/books");

        response.then().assertThat().statusCode(200);

        String bookId=response.jsonPath().getString("[0].id");

        return bookId;


    }
    public static String generateBearerToken(){
        Faker faker=new Faker();
        //We generated information for request body
        String clientName=faker.name().fullName();
        String clientEmail=faker.internet().emailAddress();

        JSONObject json=new JSONObject();
        json.put("clientName",clientName);
        json.put("clientEmail",clientEmail);

        String requestPayload=json.toString();

        //Given
        RequestSpecification generateTokenRequest=given().header("Content-Type","application/json")
                .body(requestPayload);

        Response generateTokenResponse=generateTokenRequest.when().post("https://simple-books-api.glitch.me/api-clients");

        System.out.println(generateTokenResponse.getBody().asString());
        generateTokenResponse.then().assertThat().statusCode(201);

        return "Bearer "+generateTokenResponse.jsonPath().getString("accessToken");


    }

}
