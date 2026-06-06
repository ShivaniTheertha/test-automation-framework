package com.orangehrm.stepdefinitions;

import com.orangehrm.pages.DashBoard;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private LoginPage loginPage;
    private DashBoard dashBoard;

    @Given("the user is on the OrangeHRM login page")
    public void the_user_is_on_the_orange_hrm_login_page() {
        loginPage = new LoginPage();
        Assert.assertEquals(loginPage.verifyLoginPage(), "Login", "User is not on the OrangeHRM login page");
    }

    @When("the user logs in with valid credentials")
    public void the_user_logs_in_with_valid_credentials() {
        loginPage.enterUsername(ConfigReader.get("admin.username"));
        loginPage.enterPassword(ConfigReader.get("admin.password"));
        loginPage.clickLoginButton();
    }

    @When("the user logs in with username {string} and password")
    public void the_user_logs_in_with_username_and_password(String username) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(ConfigReader.get("admin.password"));
        loginPage.clickLoginButton();
    }

    @When("the user logs in with username {string} and password {string}")
    public void the_user_logs_in_with_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    @Then("the user should be redirected to the OrangeHRM dashboard page")
    public void the_user_should_be_redirected_to_the_orangehrm_dashboard_page() {
        dashBoard = new DashBoard();
        Assert.assertEquals(dashBoard.verifyDashBoardHeader(), "Dashboard", "User is not on the OrangeHRM dashboard page");
    }

    @Then("error message {string} should be displayed")
    public void error_message_should_be_displayed(String errorMessage) {
        Assert.assertEquals(loginPage.getInvalidCredentialsMessage(), errorMessage, "Error message for invalid credentials is not displayed correctly");
    }


    @Then("the username field should show error {string}")
    public void the_username_field_should_show_error(String errorMessage) {
        Assert.assertEquals(loginPage.getUsernameRequiredMessage(), errorMessage, "Error message for username required is not displayed correctly");
    }


    @Then("the password field should show error {string}")
    public void the_password_field_should_show_error(String errorMessage) {
        Assert.assertEquals(loginPage.getPasswordRequiredMessage(), errorMessage, "Error message for password required is not displayed correctly");
    }


}
