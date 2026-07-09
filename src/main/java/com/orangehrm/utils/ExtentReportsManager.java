package com.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsManager {
    // Separate instances for UI and API
    private static ExtentReports uiExtentReports;
    private static ExtentReports apiExtentReports;  // ← static field to make singleton instance

    private ExtentReportsManager() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated"
        );
    }

    // UI report — called from UI Hooks.java
    public static ExtentReports getUIInstance() {
        if (uiExtentReports == null) {
            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter("target/extentReport-UI.html");
            sparkReporter.config().setDocumentTitle(
                    "OrangeHRM UI Test Report"
            );
            sparkReporter.config().setReportName(
                    "UI Automation Test Results"
            );
            sparkReporter.config().setTheme(Theme.STANDARD);

            uiExtentReports = new ExtentReports();
            uiExtentReports.attachReporter(sparkReporter);
            uiExtentReports.setSystemInfo("Application", "OrangeHRM");
            uiExtentReports.setSystemInfo("Environment", "QA");
            uiExtentReports.setSystemInfo("Tester", "Shivani");
            uiExtentReports.setSystemInfo("Type", "UI Automation");
            uiExtentReports.setSystemInfo("Browser",
                    System.getProperty("browser", "chrome")
            );
        }
        return uiExtentReports;
    }

    // API report — called from ApiHooks.java
    public static ExtentReports getApiInstance() {
        if (apiExtentReports == null) {
            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter("target/extentReport-API.html");
            sparkReporter.config().setDocumentTitle(
                    "Restful Booker API Test Report"
            );
            sparkReporter.config().setReportName(
                    "API Automation Test Results"
            );
            sparkReporter.config().setTheme(Theme.STANDARD);

            apiExtentReports = new ExtentReports();
            apiExtentReports.attachReporter(sparkReporter);
            apiExtentReports.setSystemInfo("Application", "Restful Booker");
            apiExtentReports.setSystemInfo("Environment", "QA");
            apiExtentReports.setSystemInfo("Tester", "Shivani");
            apiExtentReports.setSystemInfo("Type", "API Automation");
            apiExtentReports.setSystemInfo("Base URL",
                    "https://restful-booker.herokuapp.com"
            );
        }
        return apiExtentReports;
    }

    public static ExtentReports getInstance() {
        return getUIInstance();
    }
}
