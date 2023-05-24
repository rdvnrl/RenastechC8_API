package code.hooks;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class booksApiHooks {

    @BeforeClass
    public void setup(){

        RestAssured.baseURI="https://simple-books-api.glitch.me";
        System.out.println("Api call for /orders started");

    }
}
