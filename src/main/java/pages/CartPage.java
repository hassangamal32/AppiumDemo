package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import utils.GestureUtils;

public class CartPage {
    AndroidDriver driver;

    public CartPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public String getProductTitle(String name) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + name + "\")")).getText();
    }

    public void swipeToRemoveProduct(String productName) {
        WebElement product = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")"));
        GestureUtils.swipeElementLeft(driver, product, 300);
    }

    public boolean isProductInCart(String productName) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // In CartPage.java

    public void swipeLeftOnProductFromCenter(String productName, int durationMs) {
        WebElement product = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")"));
        GestureUtils.swipeElementLeftFromCenter(driver, product, durationMs);
    }

    public void clickRemoveIcon() {
        WebElement removeIcon = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(27)"));
        removeIcon.click();
    }
}