package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class ProductDetailPage {
    AndroidDriver driver;

    public ProductDetailPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public String getProductTitle(String name) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + name + "\")")).getText();
    }

    public String getPrice() {
        return driver.findElement(AppiumBy.accessibilityId("test-Price")).getText();
    }

    public void addToCart() {
        driver.findElement(AppiumBy.accessibilityId("test-ADD TO CART")).click();
    }

    public void scrollToPrice() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"$15.99\"))"
        ));
    }

    public void scrollToAddToCartButton() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().description(\"test-ADD TO CART\"))"
        ));
    }

    public void openCart() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.widget.ImageView\").instance(3)"
        )).click();
    }
}
