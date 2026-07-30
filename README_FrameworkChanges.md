# MissionQA Test Automation

This repository contains a Maven-based Java Selenium + Cucumber
automation framework with separate API and UI workflows.

## Overview

-   API tests target `https://reqres.in/api` using REST Assured and
    Cucumber.
-   UI tests target `https://www.saucedemo.com` using Selenium WebDriver
    and Cucumber.
-   The project keeps API and UI execution paths separate by
    configuration and runner classes.

## Architecture

-   `pom.xml` --- Maven build and dependency management.
-   `src/main/resources/config.properties` --- runtime configuration for
    API base URL, API key, and browser choice.
-   `src/main/java/mission/config/ConfigReader.java` --- reads
    properties and supports `REQRES_API_KEY` environment override.
-   `src/main/java/mission/driver/BasePage.java` --- shared WebDriver
    reference.
-   `src/main/java/mission/driver/BrowserSetup.java` --- browser
    selection and Chrome options.
-   `src/main/java/mission/api/ApiClient.java` --- REST Assured client
    wrapper.
-   `src/main/java/mission/services/UserService.java` --- user-related
    API calls.
-   `src/main/java/mission/services/LoginService.java` --- login-related
    API calls.
-   `src/main/java/mission/utils/ResponseValidator.java` --- API
    response validation.
-   `src/test/java/hooks/Hook.java` --- Cucumber hooks; initializes
    browser only for UI tests.
-   `src/test/java/steps/APISteps.java` --- API step definitions.
-   `src/test/java/steps/UISteps.java` --- UI step definitions.
-   `src/test/java/runner/ApiRunnerTest.java` --- API test runner.
-   `src/test/java/runner/UiRunnerTest.java` --- UI test runner.
-   `src/test/resources/API-Test.feature` --- API feature file.
-   `src/test/resources/UI-Test.feature` --- UI feature file.

## Important Configuration

### `src/main/resources/config.properties`

-   For API test execution:
    -   set `Browser=api`
    -   remove or do not use `Browser=chrome`
-   For UI test execution:
    -   set `Browser=chrome`
-   Ensure `api.key` contains a valid ReqRes API key when running API
    tests.

Example API config:

``` properties
base.url=https://reqres.in/api
api.key=YOUR_VALID_REQRES_API_KEY
Browser=api
url=https://www.saucedemo.com
```
