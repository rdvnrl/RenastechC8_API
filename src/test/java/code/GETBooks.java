package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GETBooks {

    String baseURI="https://simple-books-api.glitch.me/books";

    @Test(description = "Given baseURI When we make a GET call to /books Then Verify status code is 200")
    public void verifyStatusCode(){
        //Given and When

        Response response= RestAssured.get(baseURI); //Right here we make the call, get response, and save it in response object
        int actualStatusCode=response.getStatusCode();
        int expectedStatusCode=200;

        System.out.println(response.getBody().asString());

        Assert.assertEquals(actualStatusCode,expectedStatusCode);
    }
    @Test(description = "Given baseURI When We make a GET call to /books and use query param limit=2 Then verify status code is 200")
    public void userRetrieveListofTheBooksLimit2(){

        //Given
        //We will build the request so we that when we make the call all relevant information is ready
        RequestSpecification request= given().queryParam("limit",2);

        //When - Here we will make the API call
        Response response = request.when().get(baseURI);

        //Then - Verify
        response.then().assertThat().statusCode(200);

        //This will print response body
        System.out.println(response.getBody().asString());
        //[{"id":1,"name":"The Russian","type":"fiction","available":true},{"id":2,"name":"Just as I Am","type":"non-fiction","available":false}]

        //Print all header?
        System.out.println(response.getHeaders());

        String actualSecondBookName=response.jsonPath().getString("[1].name");
        String actualSecondBookId=response.jsonPath().getString("[1].id");
        int intactualSecondBookId=response.jsonPath().getInt("[1].id");

        String expectedSecondBookName="Just as I Am";
        String expectedSecondBookId="2";

        System.out.println("actualSecondBookID: "+actualSecondBookId+
                "ActualSecondBookId int :"+intactualSecondBookId+
                "Actual Second Book Name"+actualSecondBookName);

        Assert.assertEquals(expectedSecondBookId,actualSecondBookId);
        Assert.assertEquals(expectedSecondBookName,actualSecondBookName);
    }
    @Test(description = "Given baseURI When we make a GET call to /books and provide limit=1 and type=fiction as paramters Then Verify Status code ")
    void userRetrieveListofBooksMultipleParameters(){
        //Given
        RequestSpecification request=given()
                .queryParams("type","fiction","limit",1);

        //When
        Response response=request.when().get(baseURI);

        //Then
        response.then().assertThat().statusCode(200);
        String type=response.jsonPath().getString("[0].type");
        System.out.println(type);
        Assert.assertEquals(type,"fiction");

        String book=response.jsonPath().getString("[0].id");
        System.out.println(book);
        Assert.assertEquals(book,"1");

        String isAvailable=response.jsonPath().getString("[0].available");
        System.out.println(isAvailable);
        Assert.assertEquals(isAvailable,"true");

      String index0=  response.jsonPath().getString("[0]");
        System.out.println(index0);

    }

    @Test(description = "Given a baseURI When We make the GET call to /books and query param as type=crime(doesnt exist) Then Verify Status Code" )
    public void userRetrieveListofBooksNegative(){
        //Given
        RequestSpecification request=given().queryParam("type","crime");

        //When
        Response response=request.when().get(baseURI);


        //Then
        response.then().assertThat().statusCode(400);
    }
    @Test(description = "Given a baseURI When We make the GET call to /books and query param as limit=100(doesnt exist) Then Verify Status Code")
      public void userRetrieveListofBooksNegativeTC2(){
        //Given
        RequestSpecification request= given().queryParam("limit",100);

        //When
        Response response=request.when().get(baseURI);


        //Then
        response.then().assertThat().statusCode(400);

    }

    @Test(description = "Given a baseURI When We make the GET call to /books and path param bookid=1 Then Verify Status Code")
    public void userRetrieveSingleBookInformation(){
        String bookId="1";
        String bookIdFromUtils=utils.getBookId();
        //Given
        RequestSpecification request=given().pathParam("bookId",bookIdFromUtils);

        //When
        Response response=request.when().get(baseURI+"/{bookId}");

        //Then
        response.then().assertThat().statusCode(200);
        System.out.println(response.getBody().asString());


    }
}
