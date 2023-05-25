package code.TestScenarios;

import code.utils.utils;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class POSTTOKEN {
  //  String URI="https://simple-books-api.glitch.me/api-clients";

    @BeforeClass
    public void setup(){

        RestAssured.baseURI="https://simple-books-api.glitch.me";

    }

    @Test(description = "Given a baseURI When we make POST call to /api-clients Then Verify Status code")
    void verifyAccessToken(){

        System.out.println(utils.generateBearerToken());


    }
}
