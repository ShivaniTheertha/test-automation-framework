package com.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsManager {
    private static ExtentReports extentReports;  // ← static field to make singleton instance

    private ExtentReportsManager() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated"
        );
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {              // ← Uses the existing extentReports instance if it exists else creates a new one
            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter("target/extentReport.html");
            sparkReporter.config().setDocumentTitle("OrangeHRM Test Report");
            sparkReporter.config().setReportName("Automation Test Results");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Application", "OrangeHRM");
            extentReports.setSystemInfo("Environment", "QA");
            extentReports.setSystemInfo("Tester", "Shivani");
            extentReports.setSystemInfo("Browser",
                    System.getProperty("browser", "chrome"));
        }
        return extentReports;
    }

}
