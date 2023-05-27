package code.utils;

import code.TestScenarios.orderBookPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    //This method will help us to read external JSON files
    public static String  readJsonFile(String path){

        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void retrieveMyOrder(String bearerToken, String customerName){
        RequestSpecification listofOrdersRequest = given()
                .header("Authorization", bearerToken);

        //When
        Response listOfOrdersResponse = listofOrdersRequest.when().get("/orders");

        //Then
        listOfOrdersResponse.then().assertThat().statusCode(200);
        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualCustomerName = listOfOrdersResponse.jsonPath().getString("[0].customerName");
        Assert.assertTrue(actualCustomerName.contains(customerName));


    }
    public static void updateMyOrder(String newName, String bearerToken, String orderId) throws JsonProcessingException {

        orderBookPojo orderBookPOJO=new orderBookPojo(newName);
        System.out.println(orderBookPOJO.toString());
        ObjectMapper objectMapper1=new ObjectMapper();

        String updateOrderJsonFromPOJO=objectMapper1.writerWithDefaultPrettyPrinter().writeValueAsString(orderBookPOJO);

        RequestSpecification updateBookRequestPOJO=given()
                .header("Content-Type","application/json")
                .header("Authorization",bearerToken)
                .pathParam("orderId",orderId)
                .body(updateOrderJsonFromPOJO);


        Response updateOrderResponsePOJO=updateBookRequestPOJO.when().patch("/orders/{orderId}");

        updateOrderResponsePOJO.then().assertThat().statusCode(204);

    }

    public static void deleteMyOrder(String orderId,String token){
        RequestSpecification deleteRequest = given()
                .pathParam("orderId", orderId)
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body("{}");

        //When
        Response deleteOrderResponse = deleteRequest.when().delete("/orders/{orderId}");

        //Then
        deleteOrderResponse.then().assertThat().statusCode(204);



    }
    public static void isOrderDeleted(String orderId, String token){


       RequestSpecification listofOrdersRequest = given()
                .header("Authorization", token);
        //When
       Response listOfOrdersResponse = listofOrdersRequest.when().get("/orders");
        //Then
        listOfOrdersResponse.then().assertThat().statusCode(200);

        System.out.println(listOfOrdersResponse.getBody().asString());

        String listofOrderResponseBody = listOfOrdersResponse.getBody().asString();
        Assert.assertTrue(!listofOrderResponseBody.contains(orderId));
        Assert.assertFalse(listofOrderResponseBody.contains(orderId));




    }

}
