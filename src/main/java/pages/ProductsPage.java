package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class ProductsPage {
    AndroidDriver driver;

    public ProductsPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void scrollToProduct(String productName) {

        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"" + productName + "\"))"
        ));
    }

    public void openProduct(String productName) {
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")")).click();
    }
}
