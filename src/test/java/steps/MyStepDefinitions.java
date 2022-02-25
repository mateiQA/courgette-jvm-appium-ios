package steps;

import io.cucumber.java8.En;
import pages.MyPage;

public class MyStepDefinitions implements En {
    MyPage myPage = new MyPage();

    public MyStepDefinitions() {
        Given("I show the alert", () -> myPage.iShowTheAlert());
        Then("I verify the alert shows {}", (String alert) -> myPage.iVerifyTheAlertShows(alert));
        And("I accept the alert", () -> myPage.iAcceptTheAlert());
    }
}
