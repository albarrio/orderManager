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

    @Given("I am a Operator of the system")
    public void iAmAOperatorOfTheSystem() {
        operator = new Operator("Mario", "Chief");
        Operator saveOperator = operatorRepository.save(operator);
        assert( saveOperator.getId() != null );
    }

    @When("There are orders in the queue")
    public void thereAreTwoOrdersInTheQueue() {
        response = RestAssured.get("http://localhost:8080/order/list/size");
        assert response != null;
        System.out.println("Response: " + response.getBody().print());
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
