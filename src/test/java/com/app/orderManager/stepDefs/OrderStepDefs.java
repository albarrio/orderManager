package com.app.orderManager.stepDefs;

import com.app.orderManager.SpringIntegrationTest;
import com.app.orderManager.model.Customer;
import com.app.orderManager.model.Order;
import com.app.orderManager.repository.CustomerRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderStepDefs extends SpringIntegrationTest {

    private Response response;
    private Customer customer;
    private Order firstOrder;

    @Autowired
    private CustomerRepository customerRepository;

    @Given("I am a registered customer")
    public void iAmARegisteredCustomer() {
        customer = new Customer("John", "jhon@gmail.com");
        Customer saveCustomer = customerRepository.save(customer);
        assert( saveCustomer.getId() != null );
        assert ( saveCustomer.getName().equals("John") );
    }

    @When("I create a new order with valid details")
    public void iCreateANewOrderWithValidDetails() {
        // Assuming the order creation endpoint is "/order/create"
        // and it accepts a JSON body with order details
        firstOrder = new Order();
        firstOrder.setCustomerId(customer.getId());

        response = RestAssured.given()
                .contentType("application/json")
                .body(firstOrder)
                .post("http://localhost:8080/order/create");
        assert response.getStatusCode() == 200;
        assert response.getBody().print() != null;
    }

    @Then("I should receive a valid order ID")
    public void iShouldReceiveAValidOrderID() {
        System.out.println("Response: " + response.getBody().print());
        assert response.getBody().print() != null;
        firstOrder.setId(Integer.parseInt(response.getBody().print()));
    }


    @And("I get the order status")
    public void iGetTheOrderStatus() {
        // Assuming the order status endpoint is "/order/status"
        // and it accepts a JSON body with order ID
        response = RestAssured.given()
                .contentType("application/json")
                .body(firstOrder.getId())
                .post("http://localhost:8080/order/status");
        assert response.getStatusCode() == 200;
        assert response.getBody().print() != null;
    }


    @And("I should see the order status as {string}")
    public void iShouldSeeTheOrderStatusAs(String statusCode) {
        System.out.println("Response: " + response.getBody().print());
        assert response.getBody().print() != null;
        assert response.getBody().print().contains(statusCode);
    }

}
