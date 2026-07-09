package com.orangehrm.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions
        (
                features = "src/test/resources/features/api",
                glue = {"com.orangehrm.stepdefinitions.api"},
                plugin = {
                        "pretty",
                        "html:target/cucumber-reports.html",
                        "json:target/cucumber-reports.json",
                        "com.orangehrm.utils.CucumberEventListener"
                },
                monochrome = true
   // ,            tags = "@api"

        )


public class ApiTestRunner extends AbstractTestNGCucumberTests {


}


