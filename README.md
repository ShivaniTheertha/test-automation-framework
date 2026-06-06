# OrangeHRM Test Automation Framework

![CI](https://github.com/ShivaniTheertha/orangehrm-test-automation
/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.22-green)
![Cucumber](https://img.shields.io/badge/Cucumber-7.33-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.8-red)
![TestNG](https://img.shields.io/badge/TestNG-7.10-blue)

## Overview

End-to-end test automation framework for the OrangeHRM application built during
a focused upskilling period. The framework follows industry best practices including
Page Object Model, BDD with Gherkin, Builder pattern for test data, and CI/CD
integration via GitHub Actions.

The project demonstrates hands-on experience with the complete automation lifecycle —
framework design, test implementation, data management, reporting, and continuous
integration.

---

## Tech Stack

| Layer           | Technology                          |
|-----------------|-----------------------------------------------------|
| Language        | Java 17                                             | 
| Browser Auto    | Selenium WebDriver 4.22                             |
| BDD Framework   | Cucumber 7.33 + Gherkin                             |
| Test Runner     | TestNG                                              |
| Build Tool      | Maven                                               |
| Reporting       | Extent Reports 5 + Cucumber Reports                 |
| Test Data       | Gson (JSON) + CSV                                   |
| CI/CD           | GitHub Actions                                      |
| Design Patterns | Page Object Model, Builder Pattern, Event Listener  |
| IDE             | IntelliJ IDEA                                       |

---

## Framework Architecture

```
src/
├── main/java/com/orangehrm/
│   ├── pages/              # Page Object classes
│   ├── models/             # POJO + Builder classes
│   └── utils/             # BrowserUtility, ConfigReader, TestDataGenerator, JsonReader
│                        
│
└── test/
    ├── java/com/orangehrm/
    │   ├── stepdefinitions/ # Cucumber step definitions + Hooks
    │   └── runners/         # TestNG Cucumber runner
    └── resources/
        ├── features/        # Gherkin feature files
        │   ├── login.feature
        │   └── employee.feature
        ├── Test Data        # JSON and CSV test data files
        └── config.properties.template
```

---

## Modules and Test Coverage

| Module    | Scenarios | Smoke | Regression | Negative | DDT |
|-----------|-----------|-------|------------|----------|-----|
| Login     | 9         | ✅    | ✅         | ✅       | ✅  |
| Employee  | 19        | ✅    | ✅         | ✅       | ✅  |
| **Total** | **28**    |       |            |          |     |

*Leave and Recruitment modules in progress — will be added shortly.*

---

## Key Design Decisions

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
---

## Running Tests Locally

### Prerequisites
- Java 17+
- Maven 3.8+
- Chrome browser

### Setup
```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/orangehrm-test-automation.git
cd orangehrm-test-automation

# Create config.properties from template
cp src/test/resources/config.properties.template \
   src/test/resources/config.properties

# Edit config.properties and fill in credentials
# admin.username=Admin
# admin.password=admin123
```

### Run commands
```bash
# Smoke tests — quick sanity check (~3 minutes)
mvn test -P smoke

# Full regression suite
mvn test -P regression

# Specific module
mvn test -Dcucumber.filter.tags=@employee
mvn test -Dcucumber.filter.tags=@login

# Cross browser
mvn test -P smoke -Dbrowser=firefox
mvn test -P smoke -Dbrowser=edge

# Headless
mvn test -P smoke -Dheadless=true
```

---

## CI/CD Pipeline

The GitHub Actions pipeline runs automatically on every push to `main`
and on every pull request.

**Pipeline stages:**
```
Push to main
    │
    ├── Smoke Tests (ubuntu-latest, Chrome headless)
    │       ↓ passes
    └── Regression Tests (ubuntu-latest, Chrome headless)
                ↓
        Upload Extent Report as build artifact
```

Credentials are stored as GitHub repository secrets — never committed to code.

---

## Reports

After each test run, reports are generated at:
```
target/cucumber-reports.html    # Cucumber built-in report
target/extent-reports/          # Extent Reports HTML dashboard
```

In GitHub Actions, reports are uploaded as build artifacts and available
for download from the Actions run summary page.

---

## Career Context

This project was built during a structured upskilling period (October 2023 — present)
following a career break after 5 years of QA automation experience at Accenture
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
QA Automation Engineer
[LinkedIn](https://linkedin.com/in/shivani-manjunath-qa/) |
[Email](mailto:shivanimanjunath91@gmail.com)
