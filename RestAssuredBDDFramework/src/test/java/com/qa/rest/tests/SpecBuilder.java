package com.qa.rest.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

	@Test
	public void specBuilderTest() {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://reqres.in").setContentType(ContentType.JSON).build();
		
		ResponseSpecification response = new ResponseSpecBuilder().expectStatusCode(200).build();
		
		RestAssured.baseURI= "https://reqres.in";
		String response1 = given().log().all().
				body("{\r\n" + 
						"  \"name\": \"morpheus\",\r\n" + 
						"  \"job\": \"leader\"\r\n" + 
						"}").
				when().
				post("/api/users").
			then().log().all().
				assertThat().
				statusCode(201).extract().response().asString();
			
			JsonPath js=new JsonPath(response1);
			String userId = js.get("id");
			System.out.println("ID of created user is : " + userId);	
		
	}
}
