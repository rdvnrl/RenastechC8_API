package code.TestScenarios;

import code.hooks.booksApiHooks;
import code.utils.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class POSTOrderBookPOJO extends booksApiHooks {
    @Test(description = "Given a baseURI and token When user wants to order a book using /orders Then Verify Status code is 201")
    public void orderABook() {
        //Order a book call
        //Needed information: Request Payload, accessToken, endpoint, and header(Content-Type)

        //Given
        //Get a Token
        String token = utils.generateBearerToken();

        //Crete Payload for Api call
        Faker faker = new Faker();
        String customerName = faker.name().fullName();
        String bookId = utils.getBookId();

        JSONObject json = new JSONObject();
        json.put("bookId", bookId);
        json.put("customerName", customerName);

        String requestBody = json.toString();

        RequestSpecification orderBookRequest = given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(requestBody);
        //When
        Response orderBookResponse = orderBookRequest.when().post("/orders");
        orderBookResponse.then().assertThat().statusCode(201);
        System.out.println("Order Book Response : " + orderBookResponse.getBody().asString());

        String orderId = orderBookResponse.jsonPath().getString("orderId");

        //RETRIEVE ORDER(S)
        //Lets make the second cal : List of ordered book(S)

        //Given
        RequestSpecification listofOrdersRequest = given()
                .header("Authorization", token);

        //When
        Response listOfOrdersResponse = listofOrdersRequest.when().get("/orders");

        //Then
        listOfOrdersResponse.then().assertThat().statusCode(200);
        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualCustomerName = listOfOrdersResponse.jsonPath().getString("[0].customerName");
        Assert.assertTrue(actualCustomerName.contains(customerName));

        //UPDATE ORDER - PATCH
        //Token, Content-Type as a header, path param, requestbody
        String newCustomerName = "Mansur";

        JSONObject objectforNewName = new JSONObject();
        objectforNewName.put("customerName", newCustomerName);
        String updateOrderRequestPayload = objectforNewName.toString();

        //Given
        RequestSpecification updateOrderRequest = given()
                .pathParam("orderId", orderId)
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(updateOrderRequestPayload);
        //When
        Response updateOrderResponse = updateOrderRequest.when().patch("/orders/{orderId}");

        //Then
        updateOrderResponse.then().assertThat().statusCode(204);

        //MAKE ANOTHER CALL TO : List of Order(s)
        listofOrdersRequest = given()
                .header("Authorization", token);
        listOfOrdersResponse = listofOrdersRequest.when().get("/orders");

        listOfOrdersResponse.then().assertThat().statusCode(200);

        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualNewCustomerName = listOfOrdersResponse.jsonPath().getString("[0].customerName");
        System.out.println(actualNewCustomerName);
        Assert.assertEquals(actualNewCustomerName, newCustomerName);

        //DELETE ORDER

        //Given
        //Token, path param, Content-Type, Delete(HTTP METHOD)

        RequestSpecification deleteRequest = given()
                .pathParam("orderId", orderId)
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body("{}");

        //When
        Response deleteOrderResponse = deleteRequest.when().delete("/orders/{orderId}");

        //Then
        deleteOrderResponse.then().assertThat().statusCode(204);


        //MAKE ANOTHER CALL TO : List of Order(s)
        //Given
        listofOrdersRequest = given()
                .header("Authorization", token);
        //When
        listOfOrdersResponse = listofOrdersRequest.when().get("/orders");
        //Then
        listOfOrdersResponse.then().assertThat().statusCode(200);

        System.out.println(listOfOrdersResponse.getBody().asString());

        String listofOrderResponseBody = listOfOrdersResponse.getBody().asString();
        Assert.assertTrue(!listofOrderResponseBody.contains(orderId));
        Assert.assertFalse(listofOrderResponseBody.contains(orderId));
    }

    @Test(description = "Given a baseURI and Authorization token and other headers" +
            " When user wants to make a call /orders Then Verify if status is 201 ")
    void orderBookUsingPojo() throws JsonProcessingException {

        //Given
        // HTTP Method(POST), Body, Token, Endpoints, other headers

        //Request payload - bookId, customerName
        Faker faker = new Faker();
        String customerName=faker.name().fullName();
        String bookId=utils.getBookId();

        //Create pojo class object - orderBookPojo, set values and use it to convert to JSON
        orderBookPojo requestBody=new orderBookPojo(bookId,customerName);

        //Lets convert requestBody(Java Object) to JSON object using Object Mapper
        ObjectMapper objectMapper=new ObjectMapper();

        String orderBookJSONPayloadFromPOJO=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody);
        System.out.println("Request Payload From POJO : "+orderBookJSONPayloadFromPOJO);
        String bearerToken=utils.generateBearerToken();

        RequestSpecification orderBookRequestPojo=given()
                .header("Content-Type","application/json")
                .header("Authorization",bearerToken)
                .body(orderBookJSONPayloadFromPOJO);

        //When
        Response orderBookResponsePayload=orderBookRequestPojo.when().post("/orders");

        //Then
        orderBookResponsePayload.then().assertThat().statusCode(201);
        String orderId=orderBookResponsePayload.jsonPath().getString("orderId");

        //RETRIEVE ORDER(S)

        RequestSpecification listofOrdersRequest = given()
                .header("Authorization", bearerToken);

        //When
        Response listOfOrdersResponse = listofOrdersRequest.when().get("/orders");

        //Then
        listOfOrdersResponse.then().assertThat().statusCode(200);
        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualCustomerName = listOfOrdersResponse.jsonPath().getString("[0].customerName");
        Assert.assertTrue(actualCustomerName.contains(customerName));

//        {
//            "bookId" : "1",
//                "bookID" : 0, >> First bug
//                "customerName" : "Cole Ortiz Jr."
//        }

        //        {
//                 "bookId" : "1",
//                "customerName" : "Cole Ortiz Jr."
//        }
                    //API SCHEMA
        //        {
        //                 "bookId"       : int or number,
        //                 "customerName" : string
        //        }

        //Api schema - Api contract

        //UPDATE ORDER

        //Create new customer name
        String newName="Nelly";
        orderBookPojo orderBookPOJO=new orderBookPojo("4",newName);
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


        //MAKE ANOTHER CALL TO : List of Order(s)
        listofOrdersRequest = given()
                .header("Authorization", bearerToken);
        listOfOrdersResponse = listofOrdersRequest.when().get("/orders");

        listOfOrdersResponse.then().assertThat().statusCode(200);

        System.out.println(listOfOrdersResponse.getBody().asString());

        String actualNewCustomerName = listOfOrdersResponse.jsonPath().getString("[0].customerName");
        System.out.println(actualNewCustomerName);
        Assert.assertEquals(actualNewCustomerName, newName);


    }
}

