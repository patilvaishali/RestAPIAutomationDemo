package com.qa.rest.tests;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import pojo.PojoClass;
import pojo.deSerialize;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetCallBDD {
	
	@Test
	public void test_NumberOfCircuitsFor2017_Season() {
		
		//given().
		//when().
		//then().
		//assert()
		
		given().
		when().
			get("http://ergast.com/api/f1/2017/Circuits.json").
		then().
			assertThat().
			statusCode(200).
			and().
			body("MRData.CircuitTable.Circuits.circuitId", hasSize(20)).
			and().
			header("Content-Length",equalTo("4551"));
		
	}

	@Test
	public void test_listUsers() {
	
		given().
		when().
			get("https://reqres.in/api/users?page=2").
		then().
			assertThat().
			statusCode(200).
			and().
			body("data.id", hasSize(6));
	}
	
	@Test
	public void test_listUsersTest() {
	
		RestAssured.baseURI= "https://reqres.in";
		String response = given().log().all().queryParam("page", "2").header("Content-Type", "appplication/json").
		when().
			get("/api/users").
		then().log().all().
			assertThat().
			statusCode(200).
			and().
			body("data.id", hasSize(6)).extract().response().asString();
		
		JsonPath js=new JsonPath(response);
		int totalpages = js.get("total_pages");
		System.out.println("Total Pages are : " + totalpages);
	}
	
	@Test
	public void test_CreateUser() {
		RestAssured.baseURI= "https://reqres.in";
		String response = given().log().all().
				body("{\r\n" + 
						"  \"name\": \"morpheus\",\r\n" + 
						"  \"job\": \"leader\"\r\n" + 
						"}").
				when().
				post("/api/users").
			then().log().all().
				assertThat().
				statusCode(201).extract().response().asString();
			
			JsonPath js=new JsonPath(response);
			String userId = js.get("id");
			System.out.println("ID of created user is : " + userId);	
	}
	
	@Test
	public void test_updateUser() {
		RestAssured.baseURI= "https://reqres.in";
		String response = given().log().all().
				body("{\r\n" + 
						"  \"name\": \"morpheus\",\r\n" + 
						"  \"job\": \"zion resident\"\r\n" + 
						"}").
				when().
				put("/api/users/2").
			then().log().all().
				assertThat().
				statusCode(200).
				and().header("Content-Length",equalTo("40")).extract().response().asString();
			
			JsonPath js=new JsonPath(response);
			String updateTime = js.get("updatedAt");
			System.out.println("Update time of user is : " + updateTime);	
	}
	
	@Test
	public void deleteUser() {
		RestAssured.baseURI= "https://reqres.in";
		String response = given().log().all().
				when().
				delete("/api/users/2").
			then().log().all().
				assertThat().
				statusCode(204).
				and().header("Content-Length",equalTo("0")).extract().response().asString();
			
			System.out.println("Deleted response is : " + response);	
	}
	
	//@Test
	public void test_CreateUserWithPojo() {
		PojoClass pj = new PojoClass();
		pj.setName("morpheus");
		pj.setJob("leader");
		
		RestAssured.baseURI= "https://reqres.in";
		deSerialize response = given().log().all().config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("appplication/json", ContentType.JSON))).
				body(pj).
				when().
				post("/api/users").as(deSerialize.class);
			
		System.out.println("Response is : " + response.toString());
		System.out.println("ID of created user is : " + response.getId());
		System.out.println("Created time of user is : " + response.getCreatedAt());
	}
}
