package com.app.orderManager.stepDefs;

import com.app.orderManager.SpringIntegrationTest;
import com.app.orderManager.model.Customer;
import com.app.orderManager.model.Operator;
import com.app.orderManager.model.Order;
import com.app.orderManager.repository.CustomerRepository;
import com.app.orderManager.repository.OperatorRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

public class CompleteOrderStepDefs extends SpringIntegrationTest {

    private Response response;
    private Customer customer;
    private Order firstOrder;
    private Order secondOrder;
    private Order orderInProgress;
    private Operator operator;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    OperatorRepository operatorRepository;

    @When("There are orders in the queue")
    public void thereAreTwoOrdersInTheQueue() {
        customer = new Customer("Tom", "tom@gmail.com");
        Customer saveCustomer = customerRepository.save(customer);
        assert( saveCustomer.getId() != null );
        assert ( saveCustomer.getName().equals("Tom") );

        firstOrder = new Order();
        firstOrder.setCustomerId(customer.getId());

        response = RestAssured.given()
                .contentType("application/json")
                .body(firstOrder)
                .post("http://localhost:8080/order/create");
        assert response.getStatusCode() == 200;
        assert response.getBody().print() != null;

        response = RestAssured.get("http://localhost:8080/order/size");
        assert response != null;
        System.out.println("Response: " + response.getBody().print());
        assert( Integer.parseInt(response.getBody().print()) > 0 ) : "There are no orders in the queue";
    }

    @Then("I can process the next order in queue")
    public void iCanProcessTheFirstOrderInQueue() {
        response = RestAssured.get("http://localhost:8080/order/process");
        assert response != null;
        System.out.println("Response: " + response.getBody().print());
        orderInProgress = new Order();
        orderInProgress.setId(Integer.parseInt(response.getBody().print()));
        assert orderInProgress.getId() != null;
    }

    @And("I can complete the order in progress")
    public void iCanCompleteTheOrderInProgress() {
        response = RestAssured.given()
                .contentType("application/json")
                .body(orderInProgress.getId())
                .post("http://localhost:8080/order/complete");
        assert response.getStatusCode() == 200;
        assert response.getBody().print() != null;
        System.out.println("Response: " + response.getBody().print());
    }

    @And("I should see the new order status as {string}")
    public void iShouldSeeTheNewOrderStatusAs(String status) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(orderInProgress.getId())
                .post("http://localhost:8080/order/status");
        assert response.getStatusCode() == 200;
        assert response.getBody().print() != null;
        assert response.getBody().print().contains(status);
    }
}
