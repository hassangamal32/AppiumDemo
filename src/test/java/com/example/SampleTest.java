package com.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Point;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class SampleTest {

    private AndroidDriver driver;
    private TouchActions touchActions;

    @BeforeMethod
    public void setUp() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setAutomationName("UiAutomator2")
                .setPlatformName("Android")
                .setPlatformVersion("15")
                .setDeviceName("RKCY4000D7T")
                .setNewCommandTimeout(Duration.ofSeconds(3600))
                .amend("appium:connectHardwareKeyboard", true);

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
            touchActions = new TouchActions(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Appium driver", e);
        }
    }

    @Test
    public void sampleTest() throws InterruptedException {
        WebElement el1 = driver.findElement(AppiumBy.accessibilityId("Clock"));
        el1.click();
        WebElement el2 = driver.findElement(AppiumBy.accessibilityId("Add alarm"));
        el2.click();
        Thread.sleep(3000); // Corrected

        // Perform swipe and tap actions
        touchActions.swipe(new Point(326, 723), new Point(330, 277), 1000);
        touchActions.swipe(new Point(603, 1876), new Point(648, 1322), 1000);
        touchActions.tap(new Point(795, 2130));
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
