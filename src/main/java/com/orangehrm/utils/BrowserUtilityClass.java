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
                    options.addArguments("--accept-lang=en-US");
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

    public static WebDriverWait getWait() {
        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );
        int timeout = isHeadless ? 20 : 10;
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
    }

    // Longer wait for headless mode where elements may take more time to load
    public static WebDriverWait getLongWait() {
        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );
        int timeout = isHeadless ? 30 : 20;
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
    }

    public static void gotoUrl(String url) {
        getDriver().manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(60));
        try {
            getDriver().get(url);
        } catch (org.openqa.selenium.TimeoutException e) {
            // Demo site sometimes slow — log warning and continue
            System.out.println("Page load timeout for: " + url
                    + " — attempting to continue");
        }
    }






}
