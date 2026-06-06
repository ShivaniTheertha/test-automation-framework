package com.orangehrm.utils;

import com.properties.BrowserSelection;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class BrowserUtilityClass {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    private BrowserUtilityClass() {
        // Constructor
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }


    public static void initDriver() {
// Read the browser from maven command line or default to "chrome" if not provided
        String browserValue = System.getProperty("browser", "chrome");
        BrowserSelection browser = BrowserSelection.fromString(browserValue);

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));


        WebDriver wd;

        switch (browser) {
            case FIREFOX:
                wd = new FirefoxDriver();
                break;
            case EDGE:
                wd = new EdgeDriver();
                break;
            case CHROME:
            default:
                ChromeOptions options = new ChromeOptions();
                if (isHeadless) {
                    options.addArguments("--headless");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--remote-allow-origins=*");
                } else {
                    options.addArguments("--start-maximized");
                }
                wd = new ChromeDriver(options);
                break;
        }

        if (isHeadless) {
            wd.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            wd.manage().window().maximize();
        }

        driver.set(wd);
    }





    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new IllegalStateException("WebDriver has not been initialized. Call initDriver() first.");
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    public static WebDriverWait initializeWait(int timeoutInSeconds) {
        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)));
        return wait.get();
    }

    public static WebDriverWait getWait() {
        WebDriverWait waitWithTimeOut = initializeWait(10);
        if (wait.get() == null) {
            throw new IllegalStateException("WebDriverWait has not been initialized. Call initializeWait() first.");
        }

        return waitWithTimeOut;
    }

    public static void gotoUrl(String url) {
        getDriver().get(url);
    }






}
