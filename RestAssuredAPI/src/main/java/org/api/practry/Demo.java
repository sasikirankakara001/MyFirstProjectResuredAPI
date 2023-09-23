package org.api.practry;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.*;

public class Demo {
	public static void main(String[] args) {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		String response = given().log().all().spec(req)
				.body("{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
						+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Frontline house\",\r\n"
						+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n"
						+ "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n"
						+ "  \"website\": \"http://google.com\",\r\n" + "  \"language\": \"French-IN\"\r\n" + "}")
				.post("maps/api/place/add/json").then().log().all().spec(res).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String place_id = js.getString("place_id");
		System.out.println(place_id);

		String gets = given().queryParam("place_id", place_id).queryParam("key", "qaclick123").log().all().spec(req)
				.get("maps/api/place/get/json").then().log().all().spec(res).extract().response().asString();

		String update = given().queryParam("key", "qaclick123").queryParam("place_id", place_id).log().all().spec(req)
				.body("{\r\n" + "\"place_id\":\"" + place_id + "\",\r\n" + "\"address\":\"70 winter walk, USA33\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.put("maps/api/place/update/json").then().log().all().spec(res).extract().response().asString();
		
		given().queryParam("key","qaclick123").log().all().spec(req).body("{\r\n"
				+ "\r\n"
				+ "    \"place_id\":\""+place_id+"\"\r\n"
				+ "}\r\n"
				+ "").delete("maps/api/place/delete/json").then().log().all().spec(res).extract().response().asString();

	}

}
