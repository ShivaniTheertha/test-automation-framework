package com.orangehrm.stepdefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.orangehrm.utils.BrowserUtilityClass;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.CucumberEventListener;
import com.orangehrm.utils.ExtentReportsManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class Hooks {

    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();  // ThreadLocal for thread safety in parallel execution
    private static final ExtentReports extentReports = ExtentReportsManager.getInstance();


    @Before
    public void setUp(Scenario scenario) {
        BrowserUtilityClass.initDriver();
        // Extent Reports setup
        ExtentTest test = extentReports.createTest(scenario.getName());
        extentTest.set(test);
        extentTest.get().assignCategory(scenario.getSourceTagNames().toArray(new String[0]));

        // Log scenario start info
        extentTest.get().log(Status.INFO,
                "<b>Scenario Started:</b> " + scenario.getName()
        );
        extentTest.get().log(Status.INFO,
                "<b>Tags:</b> " + scenario.getSourceTagNames()
        );

        BrowserUtilityClass.gotoUrl(ConfigReader.get("base.url"));
    }


    @After
    public void tearDown(Scenario scenario) {

        // Null check — if setUp failed before extentTest was set
        if (extentTest.get() == null) {
            System.err.println("ExtentTest null for: " + scenario.getName());
            BrowserUtilityClass.quitDriver();
            extentReports.flush();
            return;
        }


        if (scenario.isFailed()) {
            try {
                WebDriver currentDriver = BrowserUtilityClass.getDriver();

                // ── Exception from EventListener ──────────────────────────
                Throwable error = CucumberEventListener.stepException.get();
                String failedStep = CucumberEventListener.failedStepName.get();

                // Log failed step name
                if (failedStep != null) {
                    extentTest.get().log(Status.FAIL,
                            "<b>Failed Step:</b> " + failedStep
                    );
                }

                // Log exception type and message
                if (error != null) {
                    extentTest.get().log(Status.FAIL,
                            "<b>Exception:</b> "
                                    + error.getClass().getSimpleName()
                                    + ": " + error.getMessage()
                    );
                }

                // Log URL
                extentTest.get().log(Status.FAIL,
                        "<b>Failed on URL:</b> "
                                + currentDriver.getCurrentUrl()
                );

                // Screenshot for Cucumber report
                byte[] screenshotBytes = ((TakesScreenshot) currentDriver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(
                        screenshotBytes, "image/png", "Failure Screenshot"
                );

                // Screenshot for Extent Report
                String base64Screenshot = ((TakesScreenshot) currentDriver)
                        .getScreenshotAs(OutputType.BASE64);
                extentTest.get().fail(
                        "<b>Screenshot at point of failure:</b>",
                        MediaEntityBuilder
                                .createScreenCaptureFromBase64String(base64Screenshot)
                                .build()
                );

            } catch (Exception e) {
                extentTest.get().warning(
                        "Could not capture failure details: " + e.getMessage()
                );
            }

            extentTest.get().log(Status.FAIL,
                    "<b>" + scenario.getName() + "</b> — FAILED"
            );

        } else {
            extentTest.get().log(Status.PASS,
                    "<b>" + scenario.getName() + "</b> — PASSED"
            );
        }

        // Clear EventListener ThreadLocals
        CucumberEventListener.stepException.remove();
        CucumberEventListener.failedStepName.remove();
        extentTest.remove();  // ← clear ThreadLocal after each scenario


        BrowserUtilityClass.quitDriver();
        extentReports.flush();
    }

}
