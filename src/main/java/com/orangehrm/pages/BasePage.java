package com.orangehrm.pages;

import com.orangehrm.utils.BrowserUtilityClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public BasePage() {
        this.driver = BrowserUtilityClass.getDriver();
        this.wait = BrowserUtilityClass.getWait();
        this.js=(JavascriptExecutor)driver;
        this.actions=new Actions(driver);
    }

    // ── JavascriptExecutor helper methods ─────────────────────────────────────

    protected void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    protected void jsScrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void jsHighlight(WebElement element) {
        // Useful for debugging — highlights element in yellow
        js.executeScript(
                "arguments[0].style.border='3px solid yellow'", element
        );
    }

    protected String jsGetText(WebElement element) {
        return (String) js.executeScript(
                "return arguments[0].textContent;", element
        );
    }

    protected void jsEnterText(WebElement element, String text) {
        js.executeScript(
                "arguments[0].value=arguments[1];", element, text
        );
    }

    // ── Actions class helper methods ──────────────────────────────────────────

    protected void hoverOver(WebElement element) {
        actions.moveToElement(element).perform();
    }

    protected void doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
    }

    protected void rightClick(WebElement element) {
        actions.contextClick(element).perform();
    }

    protected void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }

    protected void clickAndHold(WebElement element) {
        actions.clickAndHold(element).perform();
    }


/*// ── Common Selenium helpers ───────────────────────────────────────────────

    protected void click(WebElement element) {
        BrowserUtilityClass.getWait()
                .until(ExpectedConditions.elementToBeClickable(element))
                .click();
    }

    protected void enterText(WebElement element, String text) {
        BrowserUtilityClass.getWait()
                .until(ExpectedConditions.visibilityOf(element))
                .clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        return BrowserUtilityClass.getWait()
                .until(ExpectedConditions.visibilityOf(element))
                .getText()
                .trim();
    }*/

}
