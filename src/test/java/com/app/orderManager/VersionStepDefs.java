package com.app.orderManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;

public class VersionStepDefs extends SpringIntegrationTest{

    private Response response;

    @When("client calls \\/version")
    public void clientCallsVersion() throws IOException, InterruptedException {
        response = RestAssured.get("http://localhost:8080/version");
    }

    @Then("client receives status code {int}")
    public void clientReceivesStatusCode(int statusCode) throws InterruptedException, IOException {
        assert(statusCode == response.getStatusCode());
    }

    @And("client receives server version {string}")
    public void clientReceivesServerVersion(String version) throws InterruptedException, IOException {
        assert( response.body().print() ).contains( version );
    }

}
