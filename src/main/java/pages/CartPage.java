package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class CartPage {
    AndroidDriver driver;

    public CartPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public String getProductTitle(String name) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + name + "\")")).getText();
    }
}
