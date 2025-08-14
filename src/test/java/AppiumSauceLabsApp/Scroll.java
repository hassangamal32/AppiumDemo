package AppiumSauceLabsApp;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.testng.Assert.assertEquals;


public class Scroll {

    AndroidDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability("platformName", "Android");
        cap.setCapability("appium:automationName", "UiAutomator2");
        cap.setCapability("appium:deviceName", "AVD1");
        cap.setCapability("appium:platformVersion", "15");
        cap.setCapability("appium:appActivity", "com.swaglabsmobileapp.MainActivity");
        cap.setCapability("appium:app", "D:/APKs/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        cap.setCapability("appium:autoGrantPermissions", true);
        cap.setCapability("appium:noReset", false);

        URL url = new URL("http://127.0.0.1:4723/");
        driver = new AndroidDriver(url, cap);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void Test1() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("test-Username")));
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().text(\"Sauce Labs Bolt T-Shirt\"))"));

        Thread.sleep(3000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Bolt T-Shirt\")")).click();
        Thread.sleep(3000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().text(\"$15.99\"))"));

        String text1 =driver.findElement(AppiumBy.accessibilityId("test-Price")).getText();

        assertEquals(text1, "$15.99");
    }

    @Test
    public void Test2() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("test-Username")));
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().text(\"Sauce Labs Onesie\"))"));
        Thread.sleep(3000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Onesie\")")).click();
        Thread.sleep(3000);
        String text1 =driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Onesie\")")).getText();

        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().description(\"test-ADD TO CART\"))"));
        Thread.sleep(3000);
        driver.findElement(AppiumBy.accessibilityId("test-ADD TO CART")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(3)")).click();

        String text2 =driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Onesie\")")).getText();
        assertEquals(text1, text2);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
