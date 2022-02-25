package runners;

import courgette.api.*;
import courgette.api.junit.Courgette;
import org.junit.runner.RunWith;

@RunWith(Courgette.class)
@CourgetteOptions(
        threads = 2,
        runLevel = CourgetteRunLevel.SCENARIO,
        reportTargetDir = "build/cucumber-reports/",
        showTestOutput = true,
        rerunFailedScenarios = true,
        rerunAttempts = 1,
        disableHtmlReport = HtmlReport.CUCUMBER_HTML,
        plugin = {CourgettePlugin.MOBILE_DEVICE_ALLOCATOR},
        mobileDevice = {
                "iPhone 12 mini",
                "iPhone 13 Pro"
        },
        cucumberOptions = @CucumberOptions(
                features = "src/test/resources/features",
                glue = "steps",
                tags = "@ios",
                plugin = {
                        "pretty",
                        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                        "json:build/cucumber-reports/cucumber.json",
                        "junit:build/cucumber-reports/cucumber-junit.xml",
                }
        ))
public class IosTestRunner {
}