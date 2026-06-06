Feature: Employee management in Orange HRM Application
  As an OrangeHRM application user
  I want to add, edit and delete employees in PIM module
  So that I can manage employee information effectively

  Background:
    Given the user is on the OrangeHRM login page
    When  the user logs in with valid credentials
    Then  the user should be redirected to the OrangeHRM dashboard page
    And   the user navigates to the PIM module

  # -----------Add Employee Scenario----------------

  @employee @add-employee @smoke @regression
  Scenario: Add a new employee with mandatory fields only
    When  the user clicks on Add Employee button
    And   the user enters first name and last name
    And   the user saves the employee
    Then  the employee should be saved successfully
    And   the user should be navigated to the personal details page

  @employee @add-employee @regression
  Scenario: Add a new employee with login credentials enabled
    When  the user clicks on Add Employee button
    And   the user enters first name and last name
    And   the user enables the create login toggle
    And   the user enters login username and password
    And   the user saves the employee
    Then  the employee should be saved successfully
    And   the user should be navigated to the personal details page

  @employee @add-employee @regression
  Scenario: Add a new employee and complete personal details
    When  the user clicks on Add Employee button
    And   the user enters first name and last name
    And   the user saves the employee
    Then  the user should be navigated to the personal details page


    When  the user selects gender
    And   the user selects marital status
    And   the user selects nationality
    And   the user enters date of birth
    And   the user saves the personal details
    Then  the personal details should be saved successfully


  @employee @negative @add-employee @regression
  Scenario: Add employee with empty first name
    When the user clicks on Add Employee button
    And  the user attempts to save without entering first name
    Then a validation message "Required" should be displayed

  @employee @negative @add-employee @regression
  Scenario: Add employee with empty last name
    When  the user clicks on Add Employee button
    And   the user attempts to save without entering last name
    Then  a validation message "Required" should be displayed


    # -----------Search Employee Scenario----------------

  @employee @search-employee @regression
  Scenario: Search employee by name
    Given a new employee has been added
    When  the user searches for the employee by name
    Then  the employee should appear in the search results

  @employee @search-employee @regression
  Scenario: Search employee by employee ID
    Given a new employee has been added
    When  the user searches for the employee by employee ID
    Then  the employee should appear in the search results

  @employee @search-employee @regression @employeecheck
  Scenario: Search employee by name and ID
    Given a new employee has been added
    When  the user searches for the employee by name and ID
    Then  the employee should appear in the search results

  @employee @negative @search-employee @regression
  Scenario: Search for a non-existent employee
    When  the user searches for the employee with an "invalid name"
    Then  no results should be displayed


  #-----------Edit Employee Scenario----------------


  @employee @edit-employee @regression @employeecheck
  Scenario: Edit employee first name successfully
    Given a new employee has been added
    When  the user opens the employee profile
    And   the user updates the first name and saves the personal details
    Then  the updated first name should be displayed

    @employee @edit-employee @negative  @regression
  Scenario: Edit employee with empty first name
    Given a new employee has been added
    When  the user opens the employee profile
    And   the user clears the first name field
    Then  a validation message "Required" should be displayed


  @employee @edit-employee @data-driven @regression
  Scenario Outline: Edit employee job details with various combinations
    Given a new employee has been added
    When  the user opens the employee profile
    And   the user navigates to the job details tab
    And   the user updates the job title to "<jobTitle>"
    And   the user updates the employment status to "<employmentStatus>"
    And   the user saves the job details
    Then  the job details should be saved successfully with "<jobTitle>" as job title and "<employmentStatus>" as employment status

    Examples:
      | jobTitle          | employmentStatus    |
      | Software Engineer | Full-Time Permanent |
      | QA Engineer       | Full-Time Contract  |
      | Content Specialist| Freelance           |

    #-----------Delete Employee Scenario----------------

  @employee @delete-employee @regression @employeecheck
  Scenario: Delete a single employee
    Given a new employee has been added
    When  the user deletes the employee from the list
    Then  a success message "Successfully Deleted" should be displayed
    And   the employee should no longer appear in the search results


  @employee @delete-employee @regression
  Scenario: Cancel employee deletion
    Given a new employee has been added
    When  the user initiates delete but cancels the confirmation
    Then  the employee should still appear in the search results




