package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    By loginPageHeader = By.cssSelector("h5.orangehrm-login-title");
    By usernameField = By.cssSelector("input[name='username']");
    By passwordField = By.cssSelector("input[name='password']");
    By loginButton = By.cssSelector("button[type='submit']");
    By InvalidCredentialsMessage = By.cssSelector("p.oxd-text.oxd-text--p.oxd-alert-content-text");
    By userNameRequiredMessage = By.xpath("//input[@name='username']/ancestor::div[contains(@class,'oxd-input-field-bottom-space')]/span[text()='Required']");
    By passwordRequiredMessage = By.xpath("//input[@name='password']/ancestor::div[contains(@class,'oxd-input-field-bottom-space')]/span[text()='Required']");


    public String verifyLoginPage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginPageHeader)).getText();

    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public String getInvalidCredentialsMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(InvalidCredentialsMessage)).getText();
    }

    public String getUsernameRequiredMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userNameRequiredMessage)).getText();
    }

    public String getPasswordRequiredMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordRequiredMessage)).getText();
    }
}
