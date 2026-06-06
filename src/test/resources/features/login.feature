Feature: Login Functionality of OrangeHRM application
  As an OrangeHRM application user
  I want to be able to log in with valid credentials
  So that I can access the application's features

  #Happy Path Scenario

  @login @smoke @regression @positive
  Scenario: Login with valid credentials
    Given the user is on the OrangeHRM login page
    When the user logs in with valid credentials
    Then the user should be redirected to the OrangeHRM dashboard page

  @login @positive @data-driven @regression
  Scenario Outline: Login with multiple case sensitive valid credentials
    Given the user is on the OrangeHRM login page
    When the user logs in with username "<username>" and password
    Then the user should be redirected to the OrangeHRM dashboard page

    Examples:
      | username   |
      | ADMIN      |
      | admin      |
      | AdMiN      |


    #Negative Scenario
  @login @negative @regression
  Scenario: Login with empty username
    Given the user is on the OrangeHRM login page
    When the user logs in with username "" and password "admin123"
    Then the username field should show error "Required"

  @login @negative @regression
  Scenario: Login with empty password
    Given the user is on the OrangeHRM login page
    When the user logs in with username "Admin" and password ""
    Then the password field should show error "Required"

  @login @negative @regression
  Scenario: Login with empty username and password
    Given the user is on the OrangeHRM login page
    When the user logs in with username "" and password ""
    Then the username field should show error "Required"
    And the password field should show error "Required"


   # ── Data driven — multiple credential combinations in one scenario ───────────
  @login @negative @data-driven @regression
  Scenario Outline: Login with multiple invalid credentials
    Given the user is on the OrangeHRM login page
    When the user logs in with username "<username>" and password "<password>"
    Then error message "Invalid credentials" should be displayed

    Examples:
   | username   |  password  |
   | Admin      |  ad12      |
   | Ad         |  admin123  |
   | Admin      |  ADMIN123  |
   | Ad         |  admin     |






