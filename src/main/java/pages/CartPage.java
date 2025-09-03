package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import utils.GestureUtils;

public class CartPage extends BasePage {
    public CartPage(AppiumDriver driver) {
        super(driver);
    }

    public String getProductTitle(String name) {
        if (isAndroid()) {
            return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + name + "\")")).getText();
        } else {
            return driver.findElement(AppiumBy.accessibilityId(name)).getText();
        }
    }

    public void swipeToRemoveProduct(String productName) {
        WebElement product;
        if (isAndroid()) {
            product = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")"));
        } else {
            product = driver.findElement(AppiumBy.accessibilityId(productName));
        }
        GestureUtils.swipeElementLeftFromCenter(driver, product, 300);
    }

    public boolean isProductInCart(String productName) {
        try {
            if (isAndroid()) {
                driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")"));
            } else {
                driver.findElement(AppiumBy.accessibilityId(productName));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void swipeLeftOnProductFromCenter(String productName, int durationMs) {
        WebElement product;
        if (isAndroid()) {
            product = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")"));
        } else {
            product = driver.findElement(AppiumBy.accessibilityId(productName));
        }
        GestureUtils.swipeElementLeftFromCenter(driver, product, durationMs);
    }

    public void clickRemoveIcon() {
        if (isAndroid()) {
            WebElement removeIcon = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(27)"));
            removeIcon.click();
        } else {
            // iOS implementation
            WebElement removeIcon = driver.findElement(AppiumBy.accessibilityId("removeButton"));
            removeIcon.click();
        }
    }
}