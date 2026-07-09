# OrangeHRM Test Automation Framework

![CI](https://github.com/ShivaniTheertha/orangehrm-test-automation
/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.22-green)
![RestAssured](https://img.shields.io/badge/RestAssured-6.0-blue)
![Cucumber](https://img.shields.io/badge/Cucumber-7.33-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.8-red)
![TestNG](https://img.shields.io/badge/TestNG-7.11-blue)

## Overview

A comprehensive end-to-end test automation framework covering both UI and API
testing, built during a focused upskilling period. The framework follows industry
best practices including Page Object Model, Service Object Model, BDD with Gherkin,
Builder pattern for test data, JSON Schema validation, and CI/CD integration via
GitHub Actions.

The project demonstrates hands-on experience with the complete automation lifecycle —
framework design, test implementation, data management, reporting, and continuous
integration across both UI and API layers.


Note on API target: OrangeHRM's official REST API v2 requires OAuth2
Authorization Code + PKCE which requires a stable private instance. 
Restful-Booker was selected as a well-documented REST API to demonstrate API testing capabilities
within the same unified framework.

---

## Tech Stack

| Layer           | Technology                                         |
|-----------------|----------------------------------------------------|
| Language        | Java 23                                            | 
| UI Automation   | Selenium WebDriver 4.22                            |
| API Automation  | RestAssured 6.0                                    |
| BDD Framework   | Cucumber 7.33 + Gherkin                            |
| Test Runner     | TestNG 7.11                                        |
| Build Tool      | Maven                                              |
| Reporting       | Extent Reports 5 + Cucumber Reports                |
| UI Test Data    | Gson (JSON) + JavaFaker                            |
| API Test Data   | Jackson 2.15(JSON) + JavaFaker                     |
| CI/CD           | GitHub Actions                                     |
| Design Patterns | Page Object Model, Builder Pattern, Event Listener |
| IDE             | IntelliJ IDEA                                      |

---

## Framework Architecture

```
src/
├── main/java/com/orangehrm/
│   ├── pages/              # UI Page Object classes
│   │   ├── BasePage.java
│   │   ├── DashBoard.java
│   │   ├── LoginPage.java
│   │   └── PIMPage.java
│   ├── models/             # UI POJO + Builder classes
│   │   ├── Employee.java
│   │   └── EmployeeDetailsUpdate.java
│   ├── api/                # API client and helpers
│   │   ├── models/
│   │   │   ├── Booking.java
│   │   │   └── BookingDates.java
│   │   ├── ApiClient.java
│   │   └── BookingApiHelper.java
│   └── utils/              # Shared utilities
│       ├── ConfigReader.java
│       ├── ExtentReportsManager.java
│       ├── TestDataGenerator.java
│       ├── BrowserUtilityClass.java
│       ├── CucumberEventListener.java
│       ├── JsonReader.java
│       └── BookingDataReader.java
│
└── test/
    ├── java/com/orangehrm/
    │   ├── stepdefinitions/
    │   │   ├── Hooks.java          # UI lifecycle hooks
    │   │   ├── LoginSteps.java
    │   │   ├── EmployeeSteps.java
    │   │   └── api/
    │   │       ├── ApiHooks.java   # API lifecycle hooks
    │   │       └── BookingSteps.java
    │   └── runners/
    │       ├── TestNGCucumberRunner.java
    │       └── ApiTestRunner.java
    └── resources/
        ├── features/
        │   ├── login.feature
        │   ├── employee.feature
        │   └── api/
        │       └── booking.feature
        ├── schemas/
        │   ├── create_booking_schema.json
        │   └── get_booking_schema.json
        ├── Test Data/
        │   ├── employee_update.json
        │   └── booking_data.json
        └── config.properties.template
```

---

## Modules and Test Coverage

UI Automation — OrangeHRM

| Module    | Scenarios | Smoke | Regression | Negative  | DDT |
|-----------|-----------|-------|------------|-----------|-----|
| Login     | 9         | ✅    | ✅        | ✅       | ✅  |
| Employee  | 19        | ✅    | ✅        | ✅       | ✅  |
| Total UI  | **28**    |       |            |          |     |

API Automation — Restful-Booker

| Module       | Scenarios | Smoke | Regression | Negative  |
|--------------|-----------|-------|------------|-----------|
| Booking CRUD | 9         | ✅    | ✅        | ✅       |
| Total API    | **9**     |       |            |          |     

Grand Total: 37 automated test scenarios

Leave and Recruitment UI modules planned for future sprints.
---

## Key Design Decisions

UI Framework

**Browser selection via Maven command line**
Browser is externalised from code entirely — passed as a system property.
No code change needed to switch browsers.
```bash
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

**Builder pattern for test data**
Employee objects are constructed using a fluent Builder — readable,
order-independent, and handles optional fields cleanly.
```java
Employee employee = new Employee.Builder()
    .firstName(TestDataGenerator.generateFirstName())
    .lastName(TestDataGenerator.generateLastName())
    .loginUsername(TestDataGenerator.generateUsername())
    .build();
```

**Unique test data per run**
All generated test data includes a timestamp-based run ID to prevent
conflicts across multiple test runs on a shared demo environment.
```
Auto_James_1604092301   ← unique every run, identifiable as test data
```

**JSON-driven test data for edit scenarios**
Update data for edit scenarios is externalised to JSON files and
deserialised via Gson into POJO classes — no hardcoded test data
in step definitions.

**Headless execution for CI**
Tests run headless in GitHub Actions via system property.
Same codebase runs headed locally for debugging.
```bash
mvn test -Dheadless=true   # CI
mvn test                    # local — browser opens
```

**Failure reporting with exception capture in Extent Reports**
Test failures are captured with full context in the Extent Report —
including the exception type and message, the URL where the failure
occurred, and a screenshot of the browser at the point of failure.

A custom `CucumberEventListener` implementing Cucumber's
`ConcurrentEventListener` interface listens to the `TestStepFinished`
event after every step. When a step fails, `result.getError()` provides
the actual exception directly from Cucumber's public API — no reflection
needed. The exception and failed step name are stored in a `ThreadLocal`
for thread safety, then read by the `@After` hook and logged to the
Extent Report.

```
Extent Report failure entry shows:
→ Exception: ElementClickInterceptedException: element click intercepted
→ Failed on URL: .../pim/editEmployee/id/42
→ Screenshot embedded inline
```

API Framework

**Service Object Model**
Mirrors Page Object Model for APIs — BookingApiHelper contains all HTTP
calls, step definitions contain assertions. Clean separation of concerns.

**Lazy Authentication**
Auth token fetched only when a protected endpoint (PUT/PATCH/DELETE) is
first called. GET and POST scenarios never trigger an auth call — reduces
unnecessary network requests.

```bash
javapublic static RequestSpecification getAuthRequestSpec() {
    if (!isAuthTokenSet) {
        authenticate();  // ← only called when needed
    }
    return given().spec(authRequestSpec);
}
```

**Builder pattern for API models**
Same Builder pattern used for Booking and BookingDates as for UI
models — consistent approach across both layers.

```bash
javaBooking booking = new Booking.Builder()
    .firstname(TestDataGenerator.generateApiFirstName())
    .totalprice(150)
    .bookingdates(dates)
    .build();
```   

**Dynamic test data generation**
Names generated fresh every run via JavaFaker, dates always generated
as future dates with checkout after checkin — reflects real booking
constraints and avoids conflicts on shared server.

**JSON Schema validation**
CREATE and GET responses validated against JSON Schema before field-level
assertions — catches API contract violations (renamed fields, wrong types)
separately from value errors.

**Map for PATCH requests**
Partial update uses Map<String, Object> instead of a full POJO — only
the fields present in the Map are serialised, preventing null/default
values from being sent to the server.

**Separate Extent Reports**
UI and API tests write to separate HTML reports — UI report includes
screenshots, API report includes exception details.

| Endpoint | Expected | Actual | Notes |
|----------|----------|--------|-------|
| POST /booking | 201 Created | 200 OK | Known API bug |
| DELETE /booking/{id} | 200 OK | 201 Created | Known API bug |
| Auth header | Authorization: Bearer | Cookie: token= | Cookie auth only |
| Accept header | setAccept(JSON) | addHeader("Accept","application/json") | setAccept appends / which server rejects |

---

## Running Tests Locally

### Prerequisites
- Java 17+
- Maven 3.8+
- Chrome browser

### Setup
```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/test-automation-framework.git
cd test-automation-framework

# Create config.properties from template
cp src/test/resources/config.properties.template \
   src/test/resources/config.properties

# Edit config.properties and fill in credentials
# admin.username=Admin
# admin.password=admin123
# api.username=admin
# api.password=password123
```

### Run commands
```bash
# Smoke tests — quick sanity check (~3 minutes)
mvn test -P smoke -Dbrowser=chrome -Dheadless=false

# Full regression suite
mvn test -P ui-regression -Dbrowser=chrome -Dheadless=true

# Specific module
mvn test -P login
mvn test -P employee

# Cross browser
mvn test -P smoke -Dbrowser=firefox
mvn test -P smoke -Dbrowser=edge
```
```bash
API Tests

bash# All API tests — no browser required
mvn test -P api

# API regression only
mvn test -P api-regression
```
Full Suite
```bash

bash# Runs both UI and API together
mvn test
```
---

## CI/CD Pipeline

The GitHub Actions pipeline runs automatically on every push to `main`
and on every pull request.

**Pipeline stages:**

GitHub Actions runs automatically on every push to main
and on every pull request.
```
Push to main
    │
    ├── UI Smoke Tests      (Chrome headless, ~3 min)
    │       ↓ passes
    ├── UI Regression Tests (Chrome headless, ~6 min)
    │
    └── API Tests           (No browser, ~1 min) ← runs independently
```

Credentials are stored as GitHub repository secrets — never committed to code.

---

## Reports

After each test run, reports are generated at:
```
target/extentReport-UI.html     # UI Extent Report with screenshots
target/extentReport-API.html    # API Extent Report with exception details
target/cucumber-reports.html    # Cucumber built-in report
```

In GitHub Actions, reports are uploaded as build artifacts and available
for download from the Actions run summary page.

---

## Career Context

This project was built during a structured upskilling period (Apr 2026 — present)
following a career break(2 years) after 5 years of QA automation experience at Accenture
(client: The Walt Disney Company).

The framework deliberately mirrors enterprise patterns used in production automation
suites — externalised configuration, data-driven testing, CI/CD integration, and
design patterns that scale beyond a single developer.

---

## Author

**Shivani K Manjunatha**
QA Automation Engineer
[LinkedIn](https://linkedin.com/in/shivani-manjunath-qa/) |
[Email](mailto:shivanimanjunath91@gmail.com)
