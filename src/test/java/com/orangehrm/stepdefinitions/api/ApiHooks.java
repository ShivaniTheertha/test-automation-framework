package com.orangehrm.stepdefinitions.api;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.orangehrm.api.ApiClient;
import com.orangehrm.utils.CucumberEventListener;
import com.orangehrm.utils.ExtentReportsManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApiHooks {

    private static final ExtentReports extentReports = ExtentReportsManager.getApiInstance();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Before("@api")
    public void beforeApiScenario(Scenario scenario) {

        ApiClient.buildSpecs();

        ExtentTest test = extentReports.createTest("[API] " + scenario.getName());
        extentTest.set(test);

        extentTest.get().assignCategory(scenario.getSourceTagNames().toArray(new String[0]));

        extentTest.get().log(Status.INFO,
                "<b>API Scenario Started:</b> " + scenario.getName());

        extentTest.get().log(Status.INFO, "<b>Tags:</b>" + scenario.getSourceTagNames());
    }

    @After("@api")
    public void afterApiScenario(Scenario scenario) {

        if (extentTest.get() == null) {
            extentReports.flush();
            return;
        }

        if (scenario.isFailed()) {
            Throwable error = CucumberEventListener.stepException.get();
            String failedStep = CucumberEventListener.failedStepName.get();

            if (failedStep != null) {
                extentTest.get().log(Status.FAIL, "<b>Failed Step:</b> " + failedStep);
            }

            if (error != null) {
                extentTest.get().log(Status.FAIL, "<b>Exception:</b>" + error.getClass().getSimpleName() + ":" + error.getMessage());
            }

            extentTest.get().log(Status.FAIL,
                    "<b>" + scenario.getName() + "</b> — FAILED");

        } else {
            extentTest.get().log(Status.PASS,
                    "<b>" + scenario.getName() + "</b> — PASSED");
        }

        CucumberEventListener.stepException.remove();
        CucumberEventListener.failedStepName.remove();

        extentTest.remove();
        extentReports.flush();
    }

    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }

}
