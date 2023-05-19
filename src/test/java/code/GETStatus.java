package code;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

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



    }




}
