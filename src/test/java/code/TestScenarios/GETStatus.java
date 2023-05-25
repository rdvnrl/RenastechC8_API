package code.TestScenarios;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
public class GETStatus {

    @Test
    public void happyPathTest(){
        // baseURI= baseURL + Endpoint = https://simple-books-api.glitch.me/status
        // HTTP Method = GET call

        //This line will help us to make the get call and save the response in Response class' object
        Response response= RestAssured.get("https://simple-books-api.glitch.me/status");

        //This line will print whole response body
        System.out.println("Response : "+response.asString());

        //This line will print status code of this call
        System.out.println("Status code : "+response.statusCode());

        //Lets print a header
        System.out.println("Content-Type : "+response.header("Content-Type"));

        //Lets print another header
        System.out.print("Content-Length"+response.header("Content-Length"));

        //Lets print Date of call
        System.out.print("Date"+ response.header("Date"));

        //Aheader that is not present
        System.out.println("Test : "+response.header("Test"));
    }

    @Test(description = "Given baseURL(baseURL+Endpoint) When we make GET call to /status endpoint Then Verify status code is 200 ")
    public void validateStatusCode(){
        //Given - When
        Response response = RestAssured.get("https://simple-books-api.glitch.me/status");

        int actualStatusCode=response.getStatusCode();
        int expectedStatusCode= 200;

        System.out.println("Actual Status Code : "+actualStatusCode);

        System.out.println("Expected Status Code : "+expectedStatusCode);

        //Then
        Assert.assertEquals(actualStatusCode,expectedStatusCode);

    }

    @Test(description = "Given baseURI(baseURL+Endpoint) When we make GET call to /status endpoint Then Verify Response time is less than 2000ms")
    public void verifyResponseTime(){
        //Given and When
        Response response=RestAssured.get("https://simple-books-api.glitch.me/status");


        //
        long actualResponseTime = response.getTime();
        int expectedresponse = 2000;

        System.out.println(actualResponseTime+"   "+expectedresponse);

       // Assert.assertEquals(actualResponseTime,expectedresponse);
        Assert.assertTrue(actualResponseTime<expectedresponse);
    //   MatcherAssert.assertThat(actualResponseTime, lessThan(expectedresponse));


    }
}
