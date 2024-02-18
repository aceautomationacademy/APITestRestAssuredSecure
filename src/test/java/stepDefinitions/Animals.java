package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static org.junit.Assert.assertEquals;


public class Animals {

 public RequestSpecification httpRequest;
 public Response response;
 public ResponseBody body;
 public String accessToken;
 public String types;
 public String unauthorizedtitle;

 @Given("i am an authenticated user")
 public void i_am_an_authenticated_user() {
  RestAssured.baseURI = "https://api.petfinder.com/v2";
  response = RestAssured.given()
          .contentType("application/x-www-form-urlencoded; charset=utf-8")
          .formParam("grant_type","client_credentials")
          .formParam("client_id","2RU9OFAhDlFJTsU2vTJBoxSSvTwTUs5iCpbHco3rV9HVwhJe5O")
          .formParam("client_secret", "ubgaqZgisGjZFybnPn328Khd7MaGbJ5yKBH7eXb4")
          .when()
          .post(RestAssured.baseURI + "/oauth2/token");
  body = response.getBody();

   String responseBody = body.asString();

   System.out.println(body.asString());
   System.out.println(responseBody);

   JsonPath jsnpath = response.jsonPath();
   accessToken = jsnpath.getJsonObject("access_token").toString();

 }

 @When("^i hit the get animals api url$")
 public void iHitTheGetAnimalsApiUrl() {
    response = RestAssured.given()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .get(RestAssured.baseURI + "/types");
  JsonPath jsnpath = response.jsonPath();

      types = jsnpath.getJsonObject("types.name[0]").toString();
     System.out.println(types);
 }

 @Then("^i get (\\d+) as the response code$")
 public void iGetAsTheResponseCode(int arg0) {

   int expectedResponseCode = response.getStatusCode();
    assertEquals(expectedResponseCode, arg0);

 }

 @Then("^i get animals in the response body of the api$")
 public void iGetAnimalsInTheResponseBodyOfTheApi() {

  assertEquals(types, "Dog");

 }

 @Given("^i am an unauthenticated user$")
 public void iAmAnUnauthenticatedUser() {
  RestAssured.baseURI = "https://api.petfinder.com/v2";
  response = RestAssured.given()
          .contentType("application/x-www-form-urlencoded; charset=utf-8")
          .when()
          .post(RestAssured.baseURI + "/oauth2/token");
  body = response.getBody();
  String responseBody = body.asString();

  System.out.println(body.asString());
  System.out.println(responseBody);


 }


 @Given("^i hit the get animals api url without access token$")
 public void iDoNotGetAnimalsInTheResponseOfTheApi() {
  RestAssured.baseURI = "https://api.petfinder.com/v2";
  response = RestAssured.given()
          .when()
          .get(RestAssured.baseURI + "/types");
  JsonPath jsnpath = response.jsonPath();

  unauthorizedtitle = jsnpath.getJsonObject("title").toString();
  assertEquals(unauthorizedtitle, "Unauthorized");
 }
}
