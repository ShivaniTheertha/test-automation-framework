package com.orangehrm.stepdefinitions;

import com.orangehrm.models.Employee;
import com.orangehrm.models.EmployeeDetailsUpdate;
import com.orangehrm.pages.DashBoard;
import com.orangehrm.pages.PIMPage;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.JsonReader;
import com.orangehrm.utils.TestDataGenerator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;


public class EmployeeSteps {

    private PIMPage pimPage;
    private DashBoard dashBoard;
    private Employee employee;
    String newFirstName;
    private EmployeeDetailsUpdate data;  // declared null — not initialised here

    // Loads JSON file
    private EmployeeDetailsUpdate getData() {
        if (data == null) {
            String filePath = "src/test/resources/Test Data/employee_update.json";
            data = JsonReader.read(filePath, EmployeeDetailsUpdate.class);
        }
        return data;
    }

    // -----------------Add Employee Steps-----------------

    @And("the user navigates to the PIM module")
    public void the_user_navigates_to_the_pim_module() {
        dashBoard = new DashBoard();
        dashBoard.selectHamburgerMenu("PIM");
        pimPage = new PIMPage();
    }

    @When("the user clicks on Add Employee button")
    public void user_clicks_on_add_employee_button() {
        pimPage.ClickOnAddEmployeeButton();
        employee = new Employee.Builder().firstName(TestDataGenerator.generateFirstName()).lastName(TestDataGenerator.generateLastName()).userName(TestDataGenerator.generateUserName()).Build();
    }

    @When("the user enters first name and last name")
    public void the_user_enters_first_name_and_last_name() {
        pimPage.enterFirstName(employee.getFirstName());
        pimPage.enterLastName(employee.getLastName());
    }

    @When("the user attempts to save without entering first name")
    public void the_user_attempts_to_save_without_entering_first_name() {
        pimPage.enterLastName(employee.getLastName());
        pimPage.clickSaveButton();
    }

    @When("the user attempts to save without entering last name")
    public void the_user_attempts_to_save_without_entering_last_name() {
        pimPage.enterFirstName(employee.getFirstName());
        pimPage.clickSaveButton();
    }

    @Then("a validation message {string} should be displayed")
    public void a_validation_message_should_be_displayed(String expectedMessage) {
        Assert.assertEquals(pimPage.getRequiredFieldErrorMessage(),expectedMessage, "Validation message is not displayed correctly");
    }

    @When("the user saves the employee")
    public void the_user_saves_the_employee() {
        pimPage.clickSaveButton();
    }

    @Then("the employee should be saved successfully")
    public void the_employee_should_be_saved_successfully() {
        Assert.assertEquals(pimPage.getMessage(), "Successfully Saved", "Employee was not saved successfully");
    }

    @Then("the user should be navigated to the personal details page")
    public void the_user_should_be_navigated_to_the_personal_details_page() {
        Assert.assertEquals(pimPage.PersonalDetailsHeader(), "Personal Details", "User is not on the Personal Details page");
        // NEED TO ADD ASSERTION TO VERIFY THAT THE CORRECT EMPLOYEE DETAILS ARE DISPLAYED
    }

    @When("the user enables the create login toggle")
    public void the_user_enables_the_create_login_toggle() {
        pimPage.toggleLoginDetails();
    }

    @When("the user enters login username and password")
    public void the_user_enters_login_username_and_password() {
        pimPage.enterUsername(employee.getUserNameName());
        pimPage.enterPassword(ConfigReader.get("admin.password"));
        pimPage.enterConfirmPassword(ConfigReader.get("admin.password"));
    }

    @When("the user selects marital status")
    public void the_user_selects_marital_status() {
        String maritalStatus = getData().getPersonalDetails().getMaritalStatus();
        pimPage.selectOptionFromDropdown("Marital Status",maritalStatus );
    }

    @When("the user enters date of birth")
    public void the_user_enters_date_of_birth() {
        String dob = getData().getPersonalDetails().getDateOfBirth();
        pimPage.enterDate("Date of Birth", dob);
    }


    @When("the user selects gender")
    public void the_user_selects_gender() {
        pimPage.selectGender(getData().getPersonalDetails().getGender());
    }


    @When("the user selects nationality")
    public void the_user_selects_nationality() {
        String nationality = getData()
                .getPersonalDetails().getNationality();
        pimPage.selectOptionFromDropdown("Nationality", nationality);
    }

    @When("the user saves the personal details")
    public void the_user_saves_the_personal_details() {
        pimPage.clickSaveButtonOnPersonalDetailsPage("Personal Details");
    }

    @Then("the personal details should be saved successfully")
    public void the_personal_details_should_be_saved_successfully() {
        Assert.assertEquals(pimPage.getMessage(), "Successfully Updated", "Personal details were not saved successfully");
    }

    //-----------------Search Employee Steps-----------------

    @Given("a new employee has been added")
    public void a_new_employee_has_been_added() {
        pimPage.ClickOnAddEmployeeButton();
        employee = new Employee.Builder().firstName(TestDataGenerator.generateFirstName()).lastName(TestDataGenerator.generateLastName()).empID(TestDataGenerator.generateEmpID()).Build();
        pimPage.enterFirstName(employee.getFirstName());
        pimPage.enterLastName(employee.getLastName());
        pimPage.getEmpID();
        pimPage.enterEmpID(employee.getEmpID());
        pimPage.getEmpID();
        pimPage.clickSaveButton();
        pimPage.verifyMessage();
        pimPage.PersonalDetailsHeader();
        pimPage.navigateToEmployeeList();
    }

    @When("the user searches for the employee by name")
    public void the_user_searches_for_the_employee_by_name() {

        pimPage.searchEmployeeByName(employee.getFirstName() + " " + employee.getLastName());
        System.out.println("Searching for employee: " + employee.getFirstName() + " " + employee.getLastName());
        pimPage.clickOnSearchButton();
    }

    @Then("the employee should appear in the search results by name")
    public void the_employee_should_appear_in_the_search_results_by_name() {
        Assert.assertEquals(pimPage.getRecordFoundText(), "(1) Record Found", "Employee search by name did not return the expected result");
    }

    @When("the user searches for the employee by employee ID")
    public void the_user_searches_for_the_employee_by_employee_id() {
        System.out.println("Searching for employee with ID: " + employee.getEmpID());
        pimPage.searchEmployeeById(employee.getEmpID());
        pimPage.clickOnSearchButton();
    }

    @When("the user searches for the employee by name and ID")
    public void the_user_searches_for_the_employee_by_name_and_id() {
        System.out.println("Searching for employee with name: " + employee.getFirstName() + " " + employee.getLastName() + " and ID: " + employee.getEmpID());
        pimPage.searchEmployeeByName(employee.getFirstName() + " " + employee.getLastName());
        pimPage.searchEmployeeById(employee.getEmpID());
        pimPage.clickOnSearchButton();
    }

    @Then("the employee should appear in the search results")
    public void the_employee_should_appear_in_the_search_results() {
        Assert.assertEquals(pimPage.getRecordFoundText(), "(1) Record Found", "Employee search by ID did not return the expected result");
        pimPage.verifySearchResultList(employee.getEmpID(), employee.getFirstName(), employee.getLastName());
    }

    @When("the user searches for the employee with an {string}")
    public void the_user_searches_for_the_employee_with_an(String name) {
        pimPage.searchEmployeeByName(name);
        pimPage.clickOnSearchButton();
    }

    @Then("no results should be displayed")
    public void no_results_should_be_displayed() {
        Assert.assertEquals(pimPage.getMessage(), "No Records Found","Search with invalid name should not return any results");
    }

    //-----------------Edit Employee Steps-----------------

    @When("the user opens the employee profile")
    public void the_user_opens_the_employee_profile() {
        System.out.println("Searching for employee with name: " + employee.getFirstName() + " " + employee.getLastName() + " and ID: " + employee.getEmpID());
        pimPage.searchEmployeeByName(employee.getFirstName() + " " + employee.getLastName());
        pimPage.searchEmployeeById(employee.getEmpID());
        pimPage.clickOnSearchButton();
        pimPage.clickOnEditButton();
        pimPage.PersonalDetailsHeader();
    }

    @When("the user updates the first name and saves the personal details")
    public void the_user_updates_the_first_name_and_saves_the_personal_details(){
        pimPage.clearFirstNameFromPersonalDetails();
        newFirstName = getData().getPersonalDetails().getFirstName();
        pimPage.editFirstNameFromPersonalDetails(newFirstName);
        System.out.println("Updated first name to: " + pimPage.getFirstNameFromPersonalDetails());
        pimPage.clickSaveButtonOnPersonalDetailsPage("Personal Details");
        System.out.println("Updated first name to: " + pimPage.getFirstNameFromPersonalDetails());
        pimPage.verifyUpdatedMessage();
    }

    @Then("the updated first name should be displayed")
    public void the_updated_first_name_should_be_displayed() {
        String firstName = pimPage.getFirstNameFromPersonalDetails();
        Assert.assertEquals(firstName, newFirstName, "Updated first name is not displayed correctly");
        Assert.assertEquals(pimPage.getNameOnPersonalDetails(), newFirstName+" " + employee.getLastName(), "Updated full name is not displayed correctly");
    }

    @When("the user navigates to the job details tab")
    public void the_user_navigates_to_the_job_details_tab() {
        pimPage.clickOnJobHamburgerMenu();
    }

    @When("the user updates the job title to {string}")
    public void the_user_updates_the_job_title_to(String jobTitle) {
        pimPage.selectOptionFromDropdown("Job Title", jobTitle);
    }

    @When("the user updates the employment status to {string}")
    public void the_user_updates_the_employment_status_to(String employmentStatus) {
        pimPage.selectOptionFromDropdown("Employment Status", employmentStatus);
    }

    @When("the user saves the job details")
    public void the_user_saves_the_job_details() {
        pimPage.clickSaveButton();
    }

    @Then("the job details should be saved successfully with {string} as job title and {string} as employment status")
    public void the_job_details_should_be_saved_successfully(String jobTitle, String employmentStatus) {
        Assert.assertEquals(pimPage.getMessage(), "Successfully Updated", "Job details were not saved successfully");
        pimPage.navigateToEmployeeList();
        pimPage.searchEmployeeByName(employee.getFirstName() + " " + employee.getLastName());
        pimPage.clickOnSearchButton();
        pimPage.verifySearchResultListEditEmployee(employee.getEmpID(), employee.getFirstName(), employee.getLastName(), jobTitle, employmentStatus);
    }

    @When("the user clears the first name field")
    public void the_user_clears_the_first_name_field() {
        pimPage.clearFirstNameFromPersonalDetails();
    }


    //-------------------Delete Employee Steps-----------------

    @When("the user deletes the employee from the list")
    public void the_user_deletes_the_employee_from_the_list() {
        pimPage.searchEmployeeByName(employee.getFirstName() + " " + employee.getLastName());
        pimPage.searchEmployeeById(employee.getEmpID());
        pimPage.clickOnSearchButton();
        pimPage.clickOnDeleteIcon();
        pimPage.clickOnConfirmDeleteButton();
    }

    @Then("a success message {string} should be displayed")
    public void a_success_message_should_be_displayed(String expectedMessage) {
        Assert.assertEquals(pimPage.getMessage(), "Successfully Deleted", "Employee was not deleted successfully");
        pimPage.waitForMessageToDisappear();
    }

    @Then("the employee should no longer appear in the search results")
    public void the_employee_should_no_longer_appear_in_the_search_results() {
       pimPage.clickOnSearchButton();
       Assert.assertEquals(pimPage.getNoRecordsFoundText(), "No Records Found", "Deleted employee still appears in search results");
    }

    @When("the user initiates delete but cancels the confirmation")
    public void the_user_initiates_delete_but_cancels_the_confirmation() {
        pimPage.searchEmployeeByName(employee.getFirstName() + " " + employee.getLastName());
        pimPage.searchEmployeeById(employee.getEmpID());
        pimPage.clickOnSearchButton();
        System.out.println(pimPage.getRecordFoundText()) ;
        pimPage.clickOnDeleteIcon();
        pimPage.clickOnCancelDeleteButton();
    }

    @Then("the employee should still appear in the search results")
    public void the_employee_should_still_appear_in_the_search_results() {
        pimPage.clickOnSearchButton();
        Assert.assertEquals(pimPage.getRecordFoundText(), "(1) Record Found", "Employee should still appear in search results after canceling delete");
    }
}
