# Selenium Automation Framework (TestNG + POM)

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.x-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.x-red.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-blue.svg)](https://maven.apache.org/)


This project is a Selenium-based automation testing framework built using Java, TestNG, and Page Object Model (POM).  
It is designed to be stable, scalable, and headless-ready, making it suitable for local execution as well as CI/CD pipelines.

---

## 🚀 Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Java |
| Automation Tool | Selenium WebDriver |
| Test Framework | TestNG |
| Design Pattern | Page Object Model (POM) |
| Build Tool | Maven |
| Browser | Google Chrome |
| Reporting | Extent Reports |
| Logging | Log4j |
| Execution Modes | Headless & Non-headless |

---

## 📂 Project Structure

```
orangehrm-automation-framework
│
├── src/main/java
│   ├── base
│   │   └── BaseTest.java
│   │
│   ├── pages
│   │   ├── LoginPage.java
│   │   ├── DashboardPage.java
│   │   └── ResetPasswordPage.java
│   │
│   └── utils
│       ├── AppURLs.java
│       ├── ExtentReportManager.java
│       ├── WaitManager.java
│       ├── WaitUtils.java
│       └── Log.java
│
├── src/main/resources
│
├── src/test/java
│   └── test
│       ├── LoginTest.java
│       ├── DashboardTest.java
│       └── ResetPasswordTest.java
│
├── src/test/resources
│
├── logs
├── reports
├── screenshots
│
├── pom.xml
├── testng.xml
└── README.md
```

---

## 🧩 Framework Features

- ✅ **Page Object Model (POM)**
- ✅ **Thread-safe WebDriver** using ThreadLocal
- ✅ **Headless Chrome** support
- ✅ **Explicit waits** & JS-based interactions
- ✅ **Parallel execution** using TestNG
- ✅ **Automatic screenshots** on failure
- ✅ **Extent Reports** integration
- ✅ **Centralized logging** with Log4j

---

## 🧪 Test Coverage

### 🔐 Login Tests
- Valid & invalid login
- Empty field validation
- Refresh & back navigation
- Forgot password flow

### 📊 Dashboard Tests
- Sidebar navigation
- Profile dropdown options
- Support page navigation
- Change password navigation
- Logout functionality
- Session handling

### 🔁 Reset Password Tests
- Empty username validation
- Cancel navigation
- Refresh behavior
- Direct access restriction
- Reset confirmation flow

---

## ⚙️ Prerequisites

- Java JDK 8+
- Maven 3.x
- Google Chrome (latest)
- Git

---

## 🛠️ Setup Instructions

### 1️⃣ Clone Repository
```bash
git clone https://github.com/Dhruv3110/Selenium-Automatic-Framework-OrangeHRM.git
cd orangehrm-automation-framework
```

### 2️⃣ Import Project
- Import as Maven Project
- Ensure JDK is configured
- Let Maven download dependencies

### 3️⃣ Verify Build
```bash
mvn clean compile
```

---

## ▶️ How to Run Tests

### Run via TestNG (IDE)
1. Right-click `testng.xml`
2. Run as TestNG Suite

### Run via Maven
```bash
mvn clean test
```

### Run Specific Test
```bash
mvn clean test -Dtest=LoginTest
```

### Run Test Groups
```bash
mvn clean test -Dgroups=smoke
mvn clean test -Dgroups=regression
```

---

## 🧠 Headless Execution

Headless execution is enabled by default using ChromeOptions in `BaseTest.java`.

```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
options.addArguments("--disable-gpu");
options.addArguments("--window-size=1920,1080");
```

**This ensures:**
- Stable execution
- CI/CD compatibility
- Faster test runs

---

## ⚡ Parallel Execution

Parallel execution is supported using TestNG.

```xml
<suite name="OrangeHRM Suite" parallel="tests" thread-count="3">
```

Each test runs with its own WebDriver instance using **ThreadLocal**.

---

## 📊 Reports, Logs & Screenshots

| Artifact | Location | Description |
|----------|----------|-------------|
| Reports | `reports/` | Extent HTML reports |
| Logs | `logs/` | Log4j execution logs |
| Screenshots | `screenshots/` | Captured on failures |

> ⚠️ **Note:** These folders are generated at runtime and ignored via `.gitignore`.

---

## 🏗️ Framework Architecture

### Base Test
```java
public class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
}
```

### Page Object Example
```java
public class LoginPage {

    private WebDriver driver;
    private By usernameField = By.id("username");

    public void enterUsername(String username) {
        WaitUtils.waitForVisible(driver, usernameField).sendKeys(username);
    }
}
```

---

## 🔍 Best Practices Implemented

- Separation of concerns
- Explicit waits over implicit waits
- Thread-safe execution
- Reusable utilities
- Centralized logging
- Robust error handling

### ThreadLocal WebDriver
- One WebDriver per test thread
- No data collision
- True parallel execution

---

## 🚀 Future Enhancements

- [ ] Cross-browser execution
- [ ] Data-driven testing
- [ ] Docker support
- [ ] CI/CD pipelines (GitHub Actions / Jenkins)
- [ ] API testing integration
- [ ] Cloud execution (BrowserStack / Sauce Labs)

---

## 🐛 Troubleshooting

### ChromeDriver mismatch
Update ChromeDriver dependency

### Headless failures
```java
options.addArguments("--window-size=1920,1080");
```

### Element not found
- Increase explicit wait
- Validate locator

---

## 📝 Contributing

1. Fork the repo
2. Create feature branch
3. Commit changes
4. Push & raise PR

---

## 👤 Author

**Dhruv Gupta**  
B.Tech CSE | Selenium Automation Engineer

- 🔗 [GitHub](https://github.com/Dhruv3110)
- 💼 [LinkedIn](https://www.linkedin.com/in/dhruv-gupta-794968244/)
- 🌐 [Portfolio](https://dhruvgupta-dev.web.app/)

---

## ⭐ Star This Repository

If you find this framework useful, please give it a ⭐

---

**Last Updated:** February 2026 ✅
