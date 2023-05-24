package code;

import code.hooks.booksApiHooks;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTOrderBook extends booksApiHooks {

//    @BeforeClass
//    public void setup(){
//        //We are setting up baseURI here for once and it will be valid for all test cases in this class
//        RestAssured.baseURI="https://simple-books-api.glitch.me";
//
//    }

    @Test(description = "Given a baseURI and token When user wants to order a book using /orders Then Verify Status code is 201")
    public void orderABook(){
        //Order a book call
        //Needed information: Request Payload, accessToken, endpoint, and header(Content-Type)

        //Given
        //Get a Token
        String token=utils.generateBearerToken();

        //Crete Payload for Api call
        Faker faker=new Faker();
        String customerName=faker.name().fullName();
        String bookId=utils.getBookId();

        JSONObject json=new JSONObject();
        json.put("bookId",bookId);
        json.put("customerName",customerName);

        String requestBody=json.toString();

        RequestSpecification orderBookRequest=given()
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body(requestBody);
        //When
        Response orderBookResponse=orderBookRequest.when().post("/orders");
        orderBookResponse.then().assertThat().statusCode(201);
        System.out.println("Order Book Response : "+orderBookResponse.getBody().asString());

        String orderId=orderBookResponse.jsonPath().getString("orderId");

        //RETRIEVE ORDER(S)
        //Lets make the second cal : List of ordered book(S)

        //Given
        RequestSpecification listofOrdersRequest=given()
                .header("Authorization",token);

        //When
        Response listOfOrdersResponse=listofOrdersRequest.when().get("/orders");

        //Then
        listOfOrdersResponse.then().assertThat().statusCode(200);
        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualCustomerName=listOfOrdersResponse.jsonPath().getString("[0].customerName");
        Assert.assertTrue(actualCustomerName.contains(customerName));
    }



}
