package pages;

import drivermanager.DriverManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.String.format;

public class MyPage extends BasePage {

    @iOSXCUITFindBy(accessibility = "show alert")
    MobileElement showAlertBtn;

    @iOSXCUITFindBy(accessibility = "OK")
    MobileElement okBtn;

    public void iShowTheAlert() {
        clickElement(showAlertBtn);
    }

    public void iVerifyTheAlertShows(String alertText) {
        MobileElement element = (MobileElement) driver.findElementByXPath(format("//XCUIElementTypeStaticText[@value='%s']", alertText));
        waitForTextToBePresent(element, alertText);
    }

    public void iAcceptTheAlert() {
        clickElement(okBtn);
    }
}
