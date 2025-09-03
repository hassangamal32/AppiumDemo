package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.DriverUtils;

public class ProductsPage extends BasePage {
    private DriverUtils driverUtils;
    private final By titlePage;

    public ProductsPage(AppiumDriver driver) {
        super(driver);
        this.driverUtils = new DriverUtils(driver, 10);

        // Platform-specific locators
        if (isAndroid()) {
            titlePage = AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")");
        } else {
            // iOS locator
            titlePage = AppiumBy.accessibilityId("Products");
        }
    }

    public void scrollToProduct(String productName) {
        driverUtils.waitForVisibility(titlePage);
        if (isAndroid()) {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().text(\"" + productName + "\"))"
            ));
        } else {
            // iOS scrolling implementation
            // You'll need to implement iOS-specific scrolling
        }
    }

    public void openProduct(String productName) {
        if (isAndroid()) {
            driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")")).click();
        } else {
            // iOS implementation
            driver.findElement(AppiumBy.accessibilityId(productName)).click();
        }
    }
}