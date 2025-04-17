package com.app.orderManager.stepDefs;

import com.app.orderManager.SpringIntegrationTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class VersionStepDefs extends SpringIntegrationTest {

    private Response response;

    @When("client calls get version")
    public void clientCallsVersion() {
        response = RestAssured.get("http://localhost:8080/version");
        assert response != null;
    }

    @Then("client receives status code {int}")
    public void clientReceivesStatusCode(int statusCode) {
        assert(statusCode == response.getStatusCode());
    }

    @And("client receives server version {string}")
    public void clientReceivesServerVersion(String version) {
        assert( response.body().print() ).contains( version );
    }

}
