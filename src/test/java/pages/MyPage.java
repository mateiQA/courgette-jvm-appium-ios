package pages;

import drivermanager.DriverManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import static java.lang.String.format;

public class MyPage extends BasePage {
    @iOSXCUITFindBy(accessibility = "show alert")
    MobileElement element1;

    public MyPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    public void iShowTheAlert() {
        clickElement(element1);
    }

    public void iVerifyTheAlertShows(String alert) {
        MobileElement element = (MobileElement) driver.findElementByXPath(format("//XCUIElementTypeStaticText[@value='%s']", alert));
        String alertText = element.getText();
        Assert.assertEquals(alert, alertText);
    }

    public void iAcceptTheAlert() {
        MobileElement element = (MobileElement) driver.findElementByName(("OK"));
        clickElement(element);
    }
}
