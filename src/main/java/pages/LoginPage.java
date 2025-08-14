package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage {
    AndroidDriver driver;

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys(username);
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys(password);
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
    }
}
