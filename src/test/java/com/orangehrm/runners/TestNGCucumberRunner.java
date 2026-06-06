package com.orangehrm.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions
        (
                features = "src/test/resources/features",
                glue = {"com.orangehrm.stepdefinitions"},
//              tags= "@regression",
                plugin = {"pretty",
                        "html:target/cucumber-reports.html",
                        "json:target/cucumber-reports.json",
                        "com.orangehrm.utils.CucumberEventListener"
                },
                monochrome = true
        )

public class TestNGCucumberRunner extends AbstractTestNGCucumberTests {

    // This enables parallel scenario execution
    /*@Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }*/


}
