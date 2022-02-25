package pages;

import drivermanager.DriverManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;
import java.util.Set;

import static io.appium.java_client.touch.offset.PointOption.point;

public class BasePage {
    protected static IOSDriver driver;
    protected static final int STANDARD_TIMEOUT = 20;

    public BasePage() {
        driver = DriverManager.getDriver();
    }

    public void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(driver, STANDARD_TIMEOUT);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            driver.switchTo().alert().accept();
        }

    }

    public void clickElement(MobileElement element) {
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        hideKeyboard();
        try {
            element.click();
            System.out.println("Successfully clicked Element: " + element);
        } catch (Exception e) {
            if (e.getMessage().contains("Other element would receive the click")
                    || e.getMessage().contains("Expected condition failed: waiting for element to be clickable")
                    || e.getMessage().matches(
                    "([\\s\\S]*Element.*is not clickable at point.*because another element.*obscures it\\s[\\s\\S]*)"))

                throw new AssertionError(String.format("The following element is not clickable: [%s]", element));
            else {
                e.printStackTrace();
            }
        }

    }

    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
    }

    public void waitForElementToBeVisible(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, STANDARD_TIMEOUT);
        waitElement(element, wait);
    }

    public void waitForElementToNotBeVisible(MobileElement element) {
        try {
            new WebDriverWait(driver, STANDARD_TIMEOUT).until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            throw new AssertionError("Waiting for element " + element + " to not be visible failed. Original exception " + e.getMessage());
        }
    }

    private static void waitElement(MobileElement element, WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println("Element " + element + " is present");
        } catch (StaleElementReferenceException ex) {
            System.out.println("Let's retry this stale element " + element);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception ex) {
            throw new AssertionError("Visibility exception on element " + element + " " + ex.getMessage());
        }
    }

    public void type(MobileElement element, String value) {
        waitForElementToBeVisible(element);
        try {
            element.clear();
        } catch (InvalidElementStateException e) {
            System.out.println("Can t clear text in element " + element);
        }
        try {
            element.sendKeys(value);
            System.out.println("Successfully sent '" + value + "' to  element: " + element);

        } catch (Exception e) {
            throw new AssertionError(String.format("Error in sending [%s] to the following element: [%s]", value, element));
        }
    }

    public void waitForElementToBeClickable(MobileElement element) {
        waitForElementToBeVisible(element);
        WebDriverWait wait = new WebDriverWait(driver, STANDARD_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void tapToDismissElement(MobileElement element) {
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        TouchAction action = new TouchAction(driver);
        action.tap(
                        new TapOptions().withElement(
                                ElementOption.element(
                                        element)))
                .perform();
    }

    public void waitForTextToBePresent(MobileElement element, String expectedString) {
        waitForElementToBeVisible(element);
        WebDriverWait wait = new WebDriverWait(driver, STANDARD_TIMEOUT);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, expectedString));
        } catch (Exception e) {
            throw new AssertionError("\nExpected the following string '" + expectedString + "'\nFound the following string'" + element.getText() + "'\nOriginal exception " + e.getMessage());
        }

    }

    public static String getSaltString(int n) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < n) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

    public void swipeRight() {
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.9);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        new TouchAction(driver).press(point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(point(endx, starty)).release().perform();
    }

    public void swipeLeft() {
        Dimension size = driver.manage().window().getSize();
        int startx = (int) (size.width * 0.8);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        new TouchAction(driver).press(point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(point(endx, starty)).release();
    }

    /**
     * method to set the context to required view.
     *
     * @param context view to be set
     */
    public void setContext(String context) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
        }
        driver.context((String) contextNames.toArray()[1]); // set context to WEBVIEW_1

        System.out.println("Current context" + driver.getContext());
    }

    public void clickBackButton() {
        driver.navigate().back(); //Closes keyboard
        //driver.navigate().back(); //Comes out of edit mode
    }

    public boolean isOniOS() {
        boolean isOnIOS = false;
        String platform = driver.getCapabilities().getCapability("platformName").toString();
        if (platform.equalsIgnoreCase("ios")) {
            isOnIOS = true;
        }
        return isOnIOS;
    }

    public String getIOSVersion() {
        return driver.getCapabilities().getCapability("platformVersion").toString();
    }

    public boolean isOnAndroid() {
        boolean isOnAndroid = false;
        String platform = driver.getCapabilities().getCapability("platformName").toString();
        if (platform.equalsIgnoreCase("android")) {
            isOnAndroid = true;
        }
        return isOnAndroid;
    }

}
