# Courgette-JVM with Appium (iOS)
An example project showing how to use Courgette-JVM with Appium to test iOS native applications.

Extras:
* Added methods for most common element interaction methods inside the BasePage class (clickElement, waitElement etc)
* Implemented Page Object design pattern classes that contains the page objects and methods (all future page classes need to extend BasePage)
* Implemented @FindBy annotations for cleaner code and Page Factory initializers
* Refactored all Appium Driver and Appium service code to reside inside DriverManager class (just call DriverManager.getDriver() to get the driver object)
* Simplified step definitions code using "implements En" and declaring the steps inside the constructor
* Modified @After method to include a screenshot when tests fail
* Added the Allure report plugin
## System Requirements

* Java 8
* MacOS
* [Appium](https://appium.io/docs/en/about-appium/getting-started/?lang=en)
* [XCode](https://developer.apple.com/xcode/) with iPhone 13 Pro and iPhone 12 mini simulators

## Test Execution
Using Gradle from the command line
````gradle
./gradlew clean test
````

Generating the report
````java
allure serve
````