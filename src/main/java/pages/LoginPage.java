package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.DriverUtils;

public class LoginPage extends BasePage {
    // Platform-specific locators
    private final By usernameField;
    private final By passwordField;
    private final By loginButton;

    private DriverUtils utils;

    public LoginPage(AppiumDriver driver) {
        super(driver);

        // Initialize platform-specific locators
        if (isAndroid()) {
            usernameField = AppiumBy.accessibilityId("test-Username");
            passwordField = AppiumBy.accessibilityId("test-Password");
            loginButton = AppiumBy.accessibilityId("test-LOGIN");
        } else {
            // iOS locators
            usernameField = AppiumBy.accessibilityId("usernameTextField");
            passwordField = AppiumBy.accessibilityId("passwordTextField");
            loginButton = AppiumBy.accessibilityId("loginButton");
        }

        this.utils = new DriverUtils(driver, 10);
    }

    public void login(String username, String password) {
        utils.sendKeys(usernameField, username);
        utils.sendKeys(passwordField, password);
        utils.click(loginButton);
    }
}