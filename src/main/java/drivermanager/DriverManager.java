package drivermanager;

import courgette.api.CourgetteMobileDeviceAllocator;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.Objects;


public class DriverManager {
    public static IOSDriver driver;
    public static AppiumDriverLocalService service;

    private DriverManager() {
    }

    public static IOSDriver getDriver() {
        if (service == null) {
            createIOSDriverLocalService();
        }
        if (driver == null) {
            driver = createIOSDriver(service.getUrl());
        }
        return driver;
    }

    private static IOSDriver createIOSDriver(URL serverURL) {
        File app = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("apps/TestApp.app.zip")).getFile());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("deviceName", CourgetteMobileDeviceAllocator.DEVICE_NAME);
        capabilities.setCapability("udid", CourgetteMobileDeviceAllocator.UDID);
        capabilities.setCapability("wdaLocalPort", CourgetteMobileDeviceAllocator.PARALLEL_PORT);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 20);
        capabilities.setCapability("noReset", true);
        return new IOSDriver(serverURL, capabilities);
    }

    private static void createIOSDriverLocalService() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingAnyFreePort();
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", "false");
        serviceBuilder.withCapabilities(cap);
        serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

        service = serviceBuilder.build();
        service.start();
    }

    public static void quit() {
        if (driver != null) {
            driver.terminateApp("io.appium.TestApp");
            driver.quit();
            service.stop();
            driver = null;
        }
    }
}