# Practice Software Testing - UI Testing Framework

A comprehensive Java-based UI testing framework using Selenium WebDriver, TestNG, and the Page Object Model pattern. This framework is designed for testing the Practice Software Testing application (https://practicesoftwaretesting.com).

## 📋 Overview

This framework implements best practices for test automation:
- **Page Object Model (POM)** - Encapsulates page elements and actions
- **Selenium WebDriver** - Industry-standard UI automation tool
- **WebDriverManager** - Automatic driver management
- **Log4j2** - Comprehensive logging
- **AssertJ** - Fluent assertions for better readability
- **Maven** - Build and dependency management

## 🎯 Test Coverage - AC1: User Login Functionality

### User Stories Covered:
- **AC1.1** - Verify user can login with valid credentials
- **AC1.2** - Verify error message appears with invalid credentials
- **AC1.3** - Verify login page displays all required elements
- **AC1.4** - Verify validation error when email field is empty
- **AC1.5** - Verify validation error when password field is empty

## 🏗️ Project Structure

```
ui-testing-framework/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
│
├── src/main/java/com/testsmith/
│   ├── config/
│   │   ├── DriverManager.java                # WebDriver lifecycle management
│   │   └── ConfigProperties.java             # Configuration properties loader
│   ├── pages/
│   │   ├── BasePage.java                     # Base page object with common methods
│   │   └── LoginPage.java                    # Login page object (AC1)
│   ├── utils/
│   │   ├── WaitUtils.java                    # Explicit wait utilities
│   │   └── ScreenshotUtils.java              # Screenshot capture utilities
│   └── constants/
│       └── AppConstants.java                 # Application constants
│
├── src/main/resources/
│   ├── application.properties                # Application configuration
│   └── log4j2.xml                            # Log4j2 configuration
│
├── src/test/java/com/testsmith/
│   ├── tests/
│   │   ├── BaseTest.java                     # Base test class with setup/teardown
│   │   └── LoginTests.java                   # AC1 login test cases
│   └── listeners/
│
├── logs/                                      # Test execution logs
├── screenshots/                               # Failed test screenshots
```

## 🛠️ Prerequisites

- **Java 11+**
- **Maven 3.6+**
- **Chrome Browser** (current version)
- **Git**

## 📦 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/jelena-2019/ui-testing-framework.git
cd ui-testing-framework
```

### 2. Install Dependencies
```bash
mvn clean install
```

## ⚙️ Configuration

### Edit `application.properties`
Located at `src/main/resources/application.properties`

```properties
# Application URL
base.url=https://practicesoftwaretesting.com

# Browser settings
browser=chrome
headless.mode=false

# Timeouts (seconds)
implicit.wait=10
explicit.wait=15

# Valid test credentials
valid.username=customer@practicesoftwaretesting.com
valid.password=welcome01

# Invalid test credentials
invalid.username=invalid@example.com
invalid.password=wrongpassword
```

## 🚀 Running Tests

### Run All Tests
```bash
mvn clean test -X
```

### Run Specific Test Class
```bash
mvn clean test -X -Dtest=LoginTests
```

### Run Specific Test Method
```bash
mvn test -Dtest=LoginTests#testLoginWithValidCredentials
```

### Run Tests in Headless Mode
Edit `application.properties`:
```properties
headless.mode=true
```
Then run:
```bash
mvn test
```

## 📊 Test Reports

After test execution:
- **Logs** - Located in `logs/` directory
- **Screenshots** - Located in `screenshots/` directory (captured on failure)

### View HTML Report
```bash
open test-output/index.html
```

## 📝 Test Cases - AC1 Details

### AC1.1: Login with Valid Credentials
**Description**: Verify user can successfully login with valid credentials

**Steps**:
1. Navigate to https://practicesoftwaretesting.com
2. Verify login form is displayed
3. Enter valid email: `customer@practicesoftwaretesting.com`
4. Enter valid password: `welcome01`
5. Click Login button
6. Verify URL changes (user is redirected away from login page)

**Expected Result**: User should be logged in and redirected to dashboard/products page

### AC1.2: Login with Invalid Credentials
**Description**: Verify error message appears when logging in with invalid credentials

**Steps**:
1. Navigate to login page
2. Enter invalid email
3. Enter invalid password
4. Click Login button
5. Verify error message is displayed

**Expected Result**: Error message should be displayed, user remains on login page

### AC1.3: Login Page Elements Loaded
**Description**: Verify all required elements are displayed on login page

**Steps**:
1. Navigate to login page
2. Verify login form is visible
3. Verify email input field is present
4. Verify password input field is present
5. Verify login button is present

**Expected Result**: All elements should be visible and accessible

### AC1.4: Empty Email Validation
**Description**: Verify validation error when email field is left empty

**Steps**:
1. Navigate to login page
2. Leave email field empty
3. Enter valid password
4. Click Login button
5. Verify validation error or user stays on login page

**Expected Result**: User should remain on login page with validation feedback

### AC1.5: Empty Password Validation
**Description**: Verify validation error when password field is left empty

**Steps**:
1. Navigate to login page
2. Enter valid email
3. Leave password field empty
4. Click Login button
5. Verify validation error or user stays on login page

**Expected Result**: User should remain on login page with validation feedback

## 🔧 Key Features

### Page Object Model
- Encapsulates UI elements and interactions
- Improves maintainability and readability
- Reduces code duplication

### Explicit Waits
- Waits for elements to be visible, clickable, or present
- Configurable wait times
- Prevents flaky tests

### Logging
- Comprehensive logging with Log4j2
- Logs test steps, actions, and errors
- Easy debugging and test failure analysis

### Screenshot Capture
- Automatic screenshot capture on test failure
- Timestamped filenames
- Organized in `screenshots/` directory

### Configuration Management
- Externalized test data and configuration
- Easy to switch environments
- Sensitive data can be kept separate

## 🐛 Troubleshooting

### WebDriver Issues
If you encounter ChromeDriver issues:
```bash
# Clear WebDriverManager cache
rm -rf ~/.wdm

# Reinstall dependencies
mvn clean install -U
```

### Test Failures
1. Check logs in `logs/test-execution.log`
2. Review screenshots in `screenshots/` directory
3. Verify test credentials in `application.properties`
4. Check if the application is accessible

### Connection Timeout
Update timeouts in `application.properties`:
```properties
explicit.wait=30
page.load.timeout=30
```

## 📚 Next Steps

### Future Test Coverage
- AC2: Product Search Functionality
- AC3: Add to Cart Functionality
- AC4: Checkout Process
- AC5: User Registration
- AC6: Wishlist Management

### Framework Enhancements
- Implement parallel test execution
- Add API testing layer
- Integrate with CI/CD pipeline (GitHub Actions, Jenkins)
- Add Allure reporting integration
- Implement retry logic for flaky tests

## 📖 Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/)
- [Page Object Model](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)
- [Practice Software Testing](https://practicesoftwaretesting.com)
- [Practice Software Testing User Stories](https://testsmith-io.github.io/practice-software-testing/#/user-stories/v5)

## 👤 Author

Created for learning and practicing UI test automation with Java and Selenium.

## 📄 License

This project is open source and available under the MIT License.

---

**Happy Testing! 🎉**