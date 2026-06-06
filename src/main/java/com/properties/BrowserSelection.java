package com.properties;

public enum BrowserSelection {
    CHROME,
    FIREFOX,
    EDGE;

  public static BrowserSelection fromString(String browser) {
        try {
            return BrowserSelection.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported browser: " + browser+ ". Supported browsers are: CHROME, FIREFOX, EDGE.");
        }
    }
}
