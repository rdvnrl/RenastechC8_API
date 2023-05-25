Feature: Get Books Endpoint Testing

  Scenario: given baseURI when we make a get call to /books endpoint
    Given We have baseURI
    When We want to make a get call to "/books" endpoint
    Then Verify status is code 200
    Then Verify response time is less than 200ms
    Then Verify that response has all books available in records