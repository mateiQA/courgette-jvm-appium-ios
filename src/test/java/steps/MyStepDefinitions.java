package steps;

import drivermanager.DriverManager;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java8.En;
import org.junit.Assert;
import pages.MyPage;

import static java.lang.String.format;

public class MyStepDefinitions implements En {
    IOSDriver driver = DriverManager.getDriver();
    MyPage myPage = new MyPage();

    public MyStepDefinitions() {
        Given("I show the alert", () -> myPage.iShowTheAlert());
        Then("I verify the alert shows {}", (String alert) -> myPage.iVerifyTheAlertShows(alert));
        And("I accept the alert", () -> myPage.iAcceptTheAlert());
    }
}
