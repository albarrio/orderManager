package com.app.orderManager;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "com.app.orderManager",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class CucumberIntegrationTest {
}
