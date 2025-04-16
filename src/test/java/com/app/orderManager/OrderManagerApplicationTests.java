package com.app.orderManager;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderManagerApplicationTests {

	@Test
	void contextLoads() {
		Response response = RestAssured.get("http://localhost:8080/version");
		Assert.assertEquals(200, response.getStatusCode());
	}

}
