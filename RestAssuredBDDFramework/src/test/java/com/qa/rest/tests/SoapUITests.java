package com.qa.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;

public class SoapUITests {

	@Test
	public void test_AddNumbers() {
		RestAssured.baseURI= "http://dneonline.com";
		String response = given().log().all().body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <tem:Add>\r\n" + 
				"         <tem:intA>123</tem:intA>\r\n" + 
				"         <tem:intB>890</tem:intB>\r\n" + 
				"      </tem:Add>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>").header("Content-Type", "text/xml").
		when().
			post("calculator.asmx").
		then().log().all().
			assertThat().statusCode(200).extract().asString();
		
		XmlPath xpath=new XmlPath(response);
		System.out.println("Addition Result is : " + xpath.getString("AddResult"));
	}
}
