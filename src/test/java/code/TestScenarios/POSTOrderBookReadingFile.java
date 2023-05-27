package code.TestScenarios;

import code.hooks.booksApiHooks;
import code.utils.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTOrderBookReadingFile extends booksApiHooks {

    @Test(description = "Given a baseURI, token, headers and path params " +
            "When user wants to Order a book Then Verify Order is success" +
            "and status code is 201")
    void orderBookReadingJSONFile() throws JsonProcessingException {

        //Given

        //Token
        String token= utils.generateBearerToken();

        //Create request Payload
        String orderBookRequestPayload=utils.readJsonFile("src/test/java/code/testData/orderBook,.json");
        System.out.println(orderBookRequestPayload);

        RequestSpecification orderBookRequest=given()
                .header("Content-Type","application/json")
                .headers("Authorization",token)
                .body(orderBookRequestPayload);

        //When
        Response orderBookResponse=orderBookRequest.when().post("/orders");

        //Then
        orderBookResponse.then().assertThat().statusCode(201);

        String orderId=orderBookResponse.jsonPath().getString("orderId");

        //Retrieve My Order
        utils.retrieveMyOrder(token,"OmerRenastech");

        //Update My Order
        Faker faker=new Faker();
        String newCustomername=faker.name().fullName();
        utils.updateMyOrder(newCustomername,token,orderId);

        //Retrieve my order for updated for updated customer name
        utils.retrieveMyOrder(token,newCustomername);

        //Delete my order
        utils.deleteMyOrder(orderId,token);

        //Check if my order is deleted
        utils.isOrderDeleted(orderId,token);

    }

}
