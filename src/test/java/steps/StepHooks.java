package steps;

import drivermanager.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class StepHooks {

    @Before
    public void before(Scenario scenario) {
        System.out.println("Starting scenario " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            }

            System.out.println("------------------------------");
            System.out.println("Scenario " + scenario.getName() + " has " + scenario.getStatus());
            System.out.println("------------------------------");
        } finally {
            DriverManager.quit();
        }
    }
}