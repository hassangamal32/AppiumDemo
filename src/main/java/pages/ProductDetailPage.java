package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.DriverUtils;

public class ProductDetailPage extends BasePage {
    private DriverUtils driverUtils;
    private final By productPrice;
    private final By addToCartButton;
    private final By cartIcon;
    private final By backButton;

    public ProductDetailPage(AppiumDriver driver) {
        super(driver);
        this.driverUtils = new DriverUtils(driver, 10);

        // Platform-specific locators
        if (isAndroid()) {
            productPrice = AppiumBy.accessibilityId("test-Price");
            addToCartButton = AppiumBy.accessibilityId("test-ADD TO CART");
            cartIcon = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(3)");
            backButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"BACK TO PRODUCTS\")");
        } else {
            // iOS locators
            productPrice = AppiumBy.accessibilityId("priceLabel");
            addToCartButton = AppiumBy.accessibilityId("addToCartButton");
            cartIcon = AppiumBy.accessibilityId("cartIcon");
            backButton = AppiumBy.accessibilityId("backButton");
        }
    }

    public String getProductTitle(String name) {
        if (isAndroid()) {
            return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + name + "\")")).getText();
        } else {
            return driver.findElement(AppiumBy.accessibilityId(name)).getText();
        }
    }

    public String getPrice() {
        return driver.findElement(productPrice).getText();
    }

    public void addToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void scrollToPrice() {
        driverUtils.waitForVisibility(backButton);
        if (isAndroid()) {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().description(\"test-Price\"))"
            ));
        } else {
            // iOS scrolling implementation
        }
    }

    public void scrollToAddToCartButton() {
        if (isAndroid()) {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().description(\"test-ADD TO CART\"))"
            ));
        } else {
            // iOS scrolling implementation
        }
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }
}