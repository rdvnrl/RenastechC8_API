package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

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






}
