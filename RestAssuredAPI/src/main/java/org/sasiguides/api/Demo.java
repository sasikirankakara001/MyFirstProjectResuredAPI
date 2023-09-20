package org.sasiguides.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.sasiguides.api.body.Body;
import org.testng.Assert;

public class Demo {
	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type", "application/json").queryParam("key", "qaclick123")
				.body(Body.rawJsonToReqJson()).when().post("/maps/api/place/add/json").then().log().all().extract()
				.response().asString();
		JsonPath js = new JsonPath(response);
		String place_id = js.get("place_id");
		String id = js.get("id");
//	System.out.println(place_id);
//	Assert.assertNotSame(id, place_id);
		String gets = given().log().all().header("Content-Type", "application/json").queryParam("key", "qaclick123")
				.queryParam("place_id", place_id).when().get("/maps/api/place/get/json").then().log().all().extract()
				.response().asString();
		
		given().log().all().header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\"70 winter walkss, USA12345\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("/maps/api/place/update/json").then().log().all().extract().response().asString();
		
		given().log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "\r\n"
				+ "    \"place_id\":\""+place_id+"\"\r\n"
				+ "}\r\n"
				+ "").when().delete("maps/api/place/delete/json").then()
		.log().all().extract().response().asString();

	}

}
