package com.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsManager {

    private ExtentReportsManager() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static ExtentReports getInstance() {

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extentReport.html");
        sparkReporter.config().setDocumentTitle("OrangeHRM Test Report");
        sparkReporter.config().setReportName("Automation Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);

        ExtentReports extent= new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "OrangeHRM");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Shivani");
        extent.setSystemInfo("Browser", System.getProperty("browser","chrome"));

        return extent;
    }


}
