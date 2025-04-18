package com.app.orderManager;

import com.app.orderManager.model.Order;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderManagerApplicationTests {

	Response response;

	@Test
	void getVersion() {
		Response response = RestAssured.get("http://localhost:8080/version");
		Assert.assertEquals(200, response.getStatusCode());
	}



	@Test
	void getOrderCreate() {
		Order firstOrder = new Order();
		firstOrder.setCustomerId(1000);

		response = RestAssured.given()
				.contentType("application/json")
				.body(firstOrder)
				.post("http://localhost:8080/order/create");
		Assert.assertEquals(200, response.getStatusCode());
		System.out.println("Response: " + response.getBody().print());
		Assert.assertTrue(response.getBody().print() != null);
		Assert.assertTrue(Integer.parseInt(response.getBody().print()) >= 0);
	}

	@Test
	void getOrderStatus() {
		response = RestAssured.given()
				.contentType("application/json")
				.body(1000)
				.post("http://localhost:8080/order/create");
		Assert.assertEquals(200, response.getStatusCode());
		System.out.println("Response: " + response.getBody().print());
		Assert.assertTrue(response.getBody().print() != null);
		Assert.assertTrue(Integer.parseInt(response.getBody().print()) >= 0);
	}

	@Test
	void getSize() {
		Response response = RestAssured.get("http://localhost:8080/order/size");
		Assert.assertEquals(200, response.getStatusCode());
		System.out.println("Response: " + response.getBody().print());
		Assert.assertTrue(Integer.parseInt(response.getBody().print()) >= 0);
	}


}
