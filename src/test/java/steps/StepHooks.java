package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class StepHooks {

    @Before
    public void before(Scenario scenario) {
        System.out.println("Starting scenario " + scenario.getName());
    }

    @After
    public void after() {
        drivermanager.DriverManager.quit();
    }
}