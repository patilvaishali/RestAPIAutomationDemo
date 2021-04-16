package stepDefination;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class StepDefination {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	//TestDataBuild data = new TestDataBuild();
	
	 @Given("^Create User payload$")
	    public void create_user_payload() throws Throwable {
	    	RestAssured.baseURI= "https://reqres.in";
	    	RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://reqres.in").setContentType(ContentType.JSON).build();
			
	    	resspec = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();
	    	
	    	res = given().spec(req).
					body("{\r\n" + 
							"  \"name\": \"morpheus\",\r\n" + 
							"  \"job\": \"leader\"\r\n" + 
							"}");
	    	System.out.println("Inside Given Method");
	    }

	    @When("^User calls Create user API with POST http request$")
	    public void user_calls_create_user_api_with_post_http_request() throws Throwable {
	    	response = res.when().post("/api/users").then().spec(resspec).extract().response();
	    	System.out.println("Inside when Method");
	    }

	    @Then("^API call is success with status code 200$")
	    public void api_call_is_success_with_status_code_200() throws Throwable {
	    	assertEquals(response.getStatusCode(), 201);
	    	System.out.println("Inside then Method");
	    }
}
