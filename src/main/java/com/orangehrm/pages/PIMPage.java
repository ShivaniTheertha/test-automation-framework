package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

public class PIMPage extends BasePage {


    // Add Employee locators
    By addEmployeeButtonLocator = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--secondary']");
    By AddEmployeeHeaderLocator = By.cssSelector(".orangehrm-card-container h6");
    By firstNameFieldLocator = By.cssSelector("input[name='firstName']");
    By lastNameFieldLocator = By.cssSelector("input[name='lastName']");
    By loginDetailsToggleLocator = By.cssSelector(".oxd-switch-input");
    By usernameFieldLocator = By.xpath(
            "//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    By passwordFieldLocator = By.xpath(
            "//label[normalize-space()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    By confirmPasswordFieldLocator = By.xpath(
            "//label[normalize-space()='Confirm Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    By saveButtonOn = By.xpath("//button[contains(@class, 'orangehrm-left-space') and @type='submit']");
    By MessageLocator = By.cssSelector("p.oxd-text--toast-message");
    By PersonalDetailsHeaderLocator = By.xpath("//h6[contains(@class,'orangehrm-main-title') and normalize-space()='Personal Details']");
    By requiredFieldErrorMessageLocator = By.cssSelector("span.oxd-input-field-error-message");
    By employeeIdFieldLocator = By.xpath("//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    // Search Employee locators
    By employeeListHeaderLocator = By.xpath("//li[contains(@class,'oxd-topbar-body-nav-tab')]/a[normalize-space()='Employee List']");
    By searchEmployeeNameFieldLocator = By.xpath("//label[contains(@class,'oxd-label') and normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    By searchEmployeeIdFieldLocator = By.xpath("//label[contains(@class,'oxd-label') and normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    By searchButtonLocator = By.cssSelector("button[type='submit']");
    By recordFoundLocator = By.cssSelector("div.orangehrm-horizontal-padding span.oxd-text");
    By searchResultlistLocator = By.cssSelector("div.oxd-table-cell");

    // Edit Employee locators
    By searchResultLocator = By.cssSelector(".oxd-table-card .oxd-table-row--clickable");
    By editButtonLocator = By.cssSelector("i.bi-pencil-fill");
    By nameOnPersonalDetailsLocator = By.cssSelector(".orangehrm-edit-employee-name h6.oxd-text");
    By getFirstNamePersonalDetailsLocator = By.cssSelector("input.orangehrm-firstname");
    By getLastNamePersonalDetailsLocator = By.cssSelector("input.orangehrm-lastname");
    By jobHamburgerMenuLocator = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and normalize-space()='Job']");
    By ReportHamburgerMenuLocator = By.xpath("//a[contains(@class,'orangehrm-tabs-item') and normalize-space()='Report-to']");
    By addSupervisorButtonLocator = By.xpath("//h6[normalize-space()='Assigned Supervisors']/ancestor::div[contains(@class,'orangehrm-action-header')]//i");
    By supervisorNameFieldLocator = By.xpath("//label[normalize-space()='Supervisor Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    By jobDetailsHeaderLocator = By.xpath("//h6[contains(@class,'orangehrm-main-title') and normalize-space()='Job Details']");
    By formLoader = By.cssSelector(".oxd-form-loader");

    //Delete Employee locators
    By deleteiconLocator=By.cssSelector("i.bi-trash");
    By deletePopUpLocator=By.cssSelector("div.orangehrm-dialog-popup");
    By deletePopUpTitleLocator=By.cssSelector("p.oxd-text--card-title");
    By deletePopUpmessageLocator=By.cssSelector("p.oxd-text--card-body");
    By confirmDeleteButtonLocator=By.cssSelector("button.oxd-button--label-danger");
    By cancelDeleteButtonLocator=By.cssSelector("div.orangehrm-modal-footer button.oxd-button--ghost");

    //------------------------------------ADD EMPLOYEE METHODS--------------------------------------------
    public void ClickOnAddEmployeeButton() {
        //click on add employee button and verify add employee page is displayed by checking the header
        wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployeeButtonLocator)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AddEmployeeHeaderLocator));
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameFieldLocator)).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameFieldLocator)).sendKeys(lastName);
    }

    public void enterEmpID(String empID) {
        WebElement empIdField = wait.until(ExpectedConditions.elementToBeClickable(employeeIdFieldLocator));
        // .clear() was not working for this field, so using JavaScript to clear the value
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", empIdField);
        // To clear the input event for Vue/React frameworks
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                empIdField
        );
        empIdField.sendKeys(empID);
    }

    public void getEmpID() {
        String empID = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeIdFieldLocator)).getAttribute("value");
        System.out.println("Employee ID: " + empID);
    }

    public void toggleLoginDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginDetailsToggleLocator)).click();
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFieldLocator)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFieldLocator)).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordFieldLocator)).sendKeys(confirmPassword);
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButtonOn)).click();
    }

    public String getMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MessageLocator)).getText();

    }

    public void verifyMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MessageLocator));
    }

    public void waitForMessageToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(MessageLocator));
    }

    public void verifyUpdatedMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MessageLocator));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(PersonalDetailsHeaderLocator));
    }

    public String PersonalDetailsHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PersonalDetailsHeaderLocator)).getText();
    }

    public void selectOptionFromDropdown(String label, String optionText) {
        By dropdownLocator = By.xpath(
                "//label[normalize-space()='" + label + "']" +
                        "/ancestor::div[contains(@class,'oxd-input-group')]" +
                        "//div[contains(@class,'oxd-select-text--active')]");
        By dropdownOptionLocator = By.xpath(
                "//div[@role='listbox']" +
                        "//div[@role='option']" +
                        "//span[normalize-space()='" + optionText + "']");
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(dropdownOptionLocator)).click();
    }

    public void enterDate(String calenderType, String date) {
        By dateLocator = By.xpath(
                "//label[normalize-space()='" + calenderType + "']" +
                        "/ancestor::div[contains(@class,'oxd-input-group')]" +
                        "//input"
        );
        WebElement dateField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(dateLocator)
        );
        dateField.clear();
        dateField.sendKeys(date);
    }

    public void selectGender(String gender) {
        By genderLocator = By.xpath(
                "//div[contains(@class,'--gender-grouped-field')]" +
                        "//label[contains(.,'" + gender + "')]" +
                        "//span[contains(@class,'oxd-radio-input')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(genderLocator)).click();
    }


    public void clickSaveButtonOnPersonalDetailsPage(String sectionName) {
        By saveLocator = By.xpath("//h6[contains(@class,'orangehrm-main-title') and normalize-space()='" + sectionName + "']/ancestor::div//button[@type='submit']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(saveLocator)).click();

    }

    public String getRequiredFieldErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldErrorMessageLocator)).getText();
    }


    //------------------------------------SEARCH EMPLOYEE METHODS--------------------------------------------

    public void navigateToEmployeeList() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(MessageLocator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeListHeaderLocator)).click();
    }

    public void searchEmployeeById(String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployeeIdFieldLocator)).sendKeys(value);
    }

    public void searchEmployeeByName(String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployeeNameFieldLocator)).sendKeys(value);
    }

    public void clickOnSearchButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButtonLocator)).click();
    }

    public String getRecordFoundText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(recordFoundLocator)).getText();
    }

    public void verifySearchResultFoundText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(recordFoundLocator));
    }

    public Boolean verifySearchResultList(String eID, String firstName, String lastName) {
        List<WebElement> expectedData = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchResultlistLocator));
        return expectedData.get(1).getText().equals(eID) && expectedData.get(2).getText().equals(firstName) && expectedData.get(3).getText().equals(lastName);
    }

    //------------------------------------EDIT EMPLOYEE METHODS--------------------------------------------

    public void clickOnSearchResult() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultLocator)).click();
    }

    public void clickOnEditButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(editButtonLocator)).click();
    }

    public String getNameOnPersonalDetails() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameOnPersonalDetailsLocator)).getText();
    }

    private void waitForLoaderToDisappear() {
        try {
            // Wait for loader to appear first (it may not always appear)
            // Then wait for it to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formLoader));
        } catch (Exception e) {
            // Loader may not appear at all — that is fine, continue
        }
    }
    public void editFirstNameFromPersonalDetails(String name) {
        waitForLoaderToDisappear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(getFirstNamePersonalDetailsLocator)).sendKeys(name);
    }

    public void clearFirstNameFromPersonalDetails() {
        waitForLoaderToDisappear();
        WebElement firstName = wait.until(ExpectedConditions.elementToBeClickable(getFirstNamePersonalDetailsLocator));

        //Click to focus
        firstName.click();

        // Step 2 — Clear
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = '';" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" + // 2. Tell Vue value changed
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));" + // 3. Tell Vue field changed
                        "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",  // 4. Tell Vue focus left field
                firstName
        );

        // Verify it actually cleared
        String valueAfterClear = firstName.getAttribute("value");
        System.out.println("Value after clear: " + valueAfterClear);

        //If still not cleared use CTRL+A DELETE
        if (!valueAfterClear.isEmpty()) {
            firstName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            firstName.sendKeys(Keys.DELETE);
        }
    }

    public String getFirstNameFromPersonalDetails() {
        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(getFirstNamePersonalDetailsLocator));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(firstName, "value"));
        return firstName.getAttribute("value");
    }

    public void editLastNameFromPersonalDetails(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(getLastNamePersonalDetailsLocator)).sendKeys(name);
    }

    public void clickOnJobHamburgerMenu() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobHamburgerMenuLocator)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(jobDetailsHeaderLocator));
    }

    public void clickOnReportHamburgerMenu() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ReportHamburgerMenuLocator)).click();
    }

    public void clickOnAddSupervisorButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addSupervisorButtonLocator)).click();
    }

    public void enterSupervisorName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(supervisorNameFieldLocator)).sendKeys("Timothy Lewis Amiano");
    }

    public void selectDetailsOptionFromDropdown(String label, String optionText) {
        By dropdownLocator = By.xpath(
                "//label[contains(@class,'oxd-label') and normalize-space()=" + label + "]/ancestor::div[contains(@class,'oxd-input-group')]" +
                        "//div[contains(@class,'oxd-select-text-input')]");
        By dropdownOptionLocator = By.xpath(
                "//div[@role='listbox']" +
                        "//div[@role='option']" +
                        "//span[normalize-space()='" + optionText + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOptionLocator)).click();

    }

    public Boolean verifySearchResultListEditEmployee(String eID, String firstName, String lastName, String jobTitle, String employmentStatus) {
        List<WebElement> expectedData = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchResultlistLocator));
        return expectedData.get(1).getText().equals(eID) && expectedData.get(2).getText().equals(firstName) && expectedData.get(3).getText().equals(lastName) &&
                expectedData.get(4).getText().equals(jobTitle) && expectedData.get(5).getText().equals(employmentStatus);
    }

    public void clickOnDeleteIcon() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteiconLocator)).click();
    }

    public String getDeletePopUpTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(deletePopUpTitleLocator)).getText();
    }

    public String getDeletePopUpMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(deletePopUpmessageLocator)).getText();
    }

    public void clickOnConfirmDeleteButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDeleteButtonLocator)).click();
    }

    public void clickOnCancelDeleteButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cancelDeleteButtonLocator)).click();
    }

    public String getNoRecordsFoundText() {
       return wait.until(ExpectedConditions.visibilityOfElementLocated(MessageLocator)).getText();
    }




}


